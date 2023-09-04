package com.barreto.stockmanagement.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetail extends DefaultExceptionDetails{
    private final String field;
    private final String fieldMessage;
}
