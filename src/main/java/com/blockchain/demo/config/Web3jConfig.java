package com.blockchain.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Bean
    public Web3j web3(@Value("${eth-node.url}") String url) {
        return Web3j.build(new HttpService(url));
    }
}
