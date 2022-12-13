package com.epam.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.epam.exceptions.ExceptionCodes.METHOD_NOT_ALLOWED_EXCEPTION;
import static com.epam.exceptions.ExceptionCodes.NOT_FOUND_EXCEPTION;
import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<Object> handleDaoExceptions(DaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.toString(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.toString(), "exception.badRequest");
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), "exception.noHandler");
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), "exeption not supported");
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}
