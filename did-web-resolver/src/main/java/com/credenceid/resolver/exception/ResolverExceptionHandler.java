package com.credenceid.resolver.exception;

import com.credenceid.resolver.dto.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Handler for various custom exceptions
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResolverExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger exceptionHandlerLogger = LoggerFactory.getLogger(ResolverExceptionHandler.class);

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Error> handleBadRequestException(
            BadRequestException ex, WebRequest request) {
        exceptionHandlerLogger.error(ex.getMessage());
        var error = new Error("101", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServerException.class})
    public ResponseEntity<Error> handleServerException(
            ServerException ex, WebRequest request) {
        exceptionHandlerLogger.error("Unexpected error occurred", ex);
        var error = new Error("102", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Error> handleOtherExceptions(
            Exception ex, WebRequest request) {
        exceptionHandlerLogger.error("Unexpected error occurred", ex);
        var error = new Error("100", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
