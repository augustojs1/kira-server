package com.augustodev.kiraserver.common.exceptions.http;

import com.augustodev.kiraserver.common.exceptions.UnauthorizedException;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UnauthorizedResponse> handleException(UnauthorizedException exc) {
        UnauthorizedResponse error = new UnauthorizedResponse(HttpStatus.FORBIDDEN.value(), exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
