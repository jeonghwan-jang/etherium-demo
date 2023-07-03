package com.blockchain.demo.domain.entity.listener.event;

import com.blockchain.demo.domain.entity.TransactionEvent;

public record TransactionEventConfirmedEvent(TransactionEvent transactionEvent) {

}
