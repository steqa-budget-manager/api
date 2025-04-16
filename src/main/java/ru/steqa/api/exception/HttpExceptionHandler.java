package ru.steqa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpExceptionResponse> handleJsonParseException(HttpMessageNotReadableException ex, WebRequest request) {
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid JSON: " + ex.getMostSpecificCause().getMessage(),
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
