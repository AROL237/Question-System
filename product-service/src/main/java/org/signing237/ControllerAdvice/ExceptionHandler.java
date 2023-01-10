package org.signing237.ControllerAdvice;

import org.signing237.Exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> NotFoundException(NotFoundException exception) throws IOException {
        Map<String,String> error = new HashMap<>();
        error.put("error message: ",exception.getMessage());
        error.put("TimeStamp: ", LocalDateTime.now().toString());
        error.put("status code: ", HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
