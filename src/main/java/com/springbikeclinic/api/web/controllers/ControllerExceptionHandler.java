package com.springbikeclinic.api.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationConstraintViolationExceptionHandler(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(
                constraintViolation -> errors.add(
                        String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage())));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> bindExceptionHandler(BindException ex) {
        return new ResponseEntity<>(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(InvalidEntityException.class)
//    public ResponseEntity<String> handleInvalidEntityException(InvalidEntityException ex) {
//        //return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        throw new NotFoundException(ex.getMessage());
//    }
}
