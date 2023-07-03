package com.blockchain.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "응답이 실패되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),
    ALREADY_EXISTS_USERNAME(HttpStatus.BAD_REQUEST, "동일한 아이디가 존재합니다."),
    ALREADY_EXISTS_WALLET(HttpStatus.BAD_REQUEST, "생성된 지갑이 존재합니다."),
    FAILED_GENERATE_WALLET(HttpStatus.INTERNAL_SERVER_ERROR, "지갑 생성에 실패하였습니다."),
    NOT_FOUND_WALLET(HttpStatus.BAD_REQUEST, "지갑이 존재하지 않습니다."),
    AMOUNT_INSUFFICIENT(HttpStatus.BAD_REQUEST, "금액이 부족합니다."),
    FAILED_WITHDRAW(HttpStatus.INTERNAL_SERVER_ERROR, "출금 요청이 실패하였습니다."),
    FAILED_GET_GAS_PRICE(HttpStatus.INTERNAL_SERVER_ERROR, "Gas Price 정보를 가져오는데 실패하였습니다.\n잠시 후에 다시 시도해주세요."),
    OVER_PAGE_SIZE(HttpStatus.BAD_REQUEST, "페이지 크기가 초과되었습니다."),
    FAILED_ENCRYPT_OR_DECRYPT(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 암복호화 과정이 실패하였습니다.");

    private final HttpStatus status;

    private final String message;
}