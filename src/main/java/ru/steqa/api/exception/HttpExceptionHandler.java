package ru.steqa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@ControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<HttpExceptionResponse> handleHttpError(IHttpException ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                ex.getStatus(),
                ex.getMessage(),
                request.getDescription(false).replaceFirst("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpValidationExceptionResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            fieldErrors.computeIfAbsent(field, k -> new ArrayList<>()).add(error.getDefaultMessage());
        });

        HttpValidationExceptionResponse response = new HttpValidationExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getDescription(false).replace("uri=", ""),
                fieldErrors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpExceptionResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Parameter has an incorrect format",
                request.getDescription(false).replaceFirst("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpExceptionResponse> handleJsonParseException(HttpMessageNotReadableException ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Bad request",
                request.getDescription(false).replaceFirst("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpExceptionResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                ex.getMessage(),
                request.getDescription(false).replaceFirst("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpExceptionResponse> handleNotFound(NoHandlerFoundException ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false).replaceFirst("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpExceptionResponse> handleGlobalException(Exception ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. " + ex.getMessage(),
                request.getDescription(false).replaceFirst("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
