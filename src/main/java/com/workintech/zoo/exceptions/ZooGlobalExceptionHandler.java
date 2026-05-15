package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZooException(ZooException zooException) {

        log.error("ZooException occurred: {}", zooException.getMessage());

        ZooErrorResponse errorResponse = new ZooErrorResponse(
                zooException.getHttpStatus().value(),
                zooException.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, zooException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleException(Exception exception) {

        log.error("Unexpected exception occurred: {}", exception.getMessage());

        ZooErrorResponse errorResponse = new ZooErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}