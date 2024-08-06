package com.archemalt.TinkoffSpringProject.exception;

import com.archemalt.TinkoffSpringProject.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class TranslateExceptionHandler {
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Response> handleInternalServerException(Exception e) {
        Response response = new Response("Error 500: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NoResourceFoundException.class})
    protected ResponseEntity<Response> handleNotFoundException(Exception e) {
        Response response = new Response("Error 404: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Response> handleAllException(Exception e) {
        Response response = new Response("Error 400: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
