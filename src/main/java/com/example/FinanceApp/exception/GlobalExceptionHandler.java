package com.example.FinanceApp.exception;

import com.example.FinanceApp.dto.exception.ErrorResponse;
import com.example.FinanceApp.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex){

        String msg= ex.getMessage();;
        HttpStatus status=HttpStatus.BAD_REQUEST;

        if("Unauthorized".equalsIgnoreCase(msg))
            status=HttpStatus.UNAUTHORIZED;
        else if("Forbidden".equalsIgnoreCase(msg))
            status=HttpStatus.FORBIDDEN;
        else if(msg!=null && msg.toLowerCase().contains("not found"))
            status=HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ErrorResponse(msg,status.value()),status);
    }
}
