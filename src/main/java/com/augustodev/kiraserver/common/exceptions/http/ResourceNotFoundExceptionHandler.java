package com.augustodev.kiraserver.common.exceptions.http;

import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResourceNotFoundResponse> handleException(ResourceNotFoundException exc) {
        ResourceNotFoundResponse error = new ResourceNotFoundResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
