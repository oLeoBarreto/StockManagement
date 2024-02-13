package com.barreto.stockmanagement.infra.handlers;

import com.barreto.stockmanagement.infra.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetail> handleBadRequestException(BadRequestException exception) {
        return new ResponseEntity<>(BadRequestExceptionDetail.builder()
                .title("Bad request exception")
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .details(exception.getClass().getName())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ImageNotFoundExceptionDetails> handleBadRequestException(ImageNotFoundException exception) {
        return new ResponseEntity<>(ImageNotFoundExceptionDetails.builder()
                .title("Image was not found")
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .details(exception.getClass().getName())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(ValidationExceptionDetail.builder()
                .title("Bad request exception, field error")
                .status(status.value())
                .message(ex.getClass().getName())
                .details("check the fields error")
                .timestamp(LocalDateTime.now())
                .field(fields)
                .fieldMessage(fieldsMessages)
                .build(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return new ResponseEntity<>(
                DefaultExceptionDetails.builder()
                        .title(ex.getCause().getMessage())
                        .status(statusCode.value())
                        .message(ex.getClass().getName())
                        .details(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), headers, statusCode
        );
    }
}
