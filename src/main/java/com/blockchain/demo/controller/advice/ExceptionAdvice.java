package com.blockchain.demo.controller.advice;

import com.blockchain.demo.common.exception.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ErrorCodeException.class)
    protected ResponseEntity<ProblemDetail> handleErrorCodeException(ErrorCodeException e) {
        log.error("ErrorCodeException: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getStatus())
            .body(ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage()));
    }
}
