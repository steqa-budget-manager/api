package ru.steqa.api.exception;

import org.springframework.http.HttpStatus;

public interface IHttpException {
    HttpStatus getStatus();
    String getMessage();
}
