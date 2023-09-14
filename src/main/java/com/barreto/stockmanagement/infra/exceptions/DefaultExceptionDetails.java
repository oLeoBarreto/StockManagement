package com.barreto.stockmanagement.infra.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@SuperBuilder
public class DefaultExceptionDetails {
    protected String title;
    protected Integer status;
    protected String message;
    protected String details;
    protected LocalDateTime timestamp;
}
