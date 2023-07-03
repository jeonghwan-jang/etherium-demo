package com.blockchain.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

@Slf4j
@Aspect
@Component
public class SubscriberLoggingAspect {

    @Before("execution(public void com.blockchain.demo.service.Web3jService.parseBlock(..))")
    public void parseBlockLogger(JoinPoint joinPoint) {
        Block block = (Block) joinPoint.getArgs()[0];
        log.info("Parse block number: {}", block.getNumber());
    }

    @Before("execution(public void com.blockchain.demo.domain.service.TransactionEventService.saveIfNotExists(..))")
    public void TransactionEventSaveLogger(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("Tracked transaction hash: {} by blockNumber: {}", args[3], args[0]);
    }
}
