package com.learn.service.member;


import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@ControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleDbActionExecutionException(DbActionExecutionException ex) {
        return new ResponseEntity<>("Sql violation: \n "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


