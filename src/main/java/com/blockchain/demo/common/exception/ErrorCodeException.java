package com.blockchain.demo.common.exception;

import com.blockchain.demo.common.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorCodeException extends RuntimeException {

    private final HttpStatus status;

    public ErrorCodeException(ErrorCode errorCode,
                              String customMessage) {

        this(errorCode.getStatus(), customMessage);
    }

    public ErrorCodeException(ErrorCode errorCode) {
        this(errorCode.getStatus(), errorCode.getMessage());
    }

    private ErrorCodeException(HttpStatus status,
                               String message) {
        super(message);
        this.status = status;
    }
}
