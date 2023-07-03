package com.blockchain.demo.aop;

import com.blockchain.demo.dto.response.WalletResponseDto;
import com.blockchain.demo.dto.response.WithdrawResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DefaultLoggingAspect {

    @AfterReturning(value = "execution(public com.blockchain.demo.dto.response.WalletResponseDto com.blockchain.demo.service.WalletManagementService.generateWallet(..))", returning = "returnObject")
    public void GenerateWalletLogger(JoinPoint joinPoint,
                                     Object returnObject) {
        WalletResponseDto responseDto = (WalletResponseDto) returnObject;
        log.info("Generated wallet address: {}", responseDto.address());
    }

    @AfterReturning(value = "execution(public com.blockchain.demo.dto.response.WithdrawResponseDto com.blockchain.demo.service.TradeManagementService.withdraw(..))", returning = "returnObject")
    public void WithdrawLogger(JoinPoint joinPoint,
                               Object returnObject) {
        WithdrawResponseDto responseDto = (WithdrawResponseDto) returnObject;
        log.info("Fired withdraw transaction hash: {}", responseDto.hash());
    }
}
