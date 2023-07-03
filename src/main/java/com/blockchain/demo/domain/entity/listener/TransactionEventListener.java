package com.blockchain.demo.domain.entity.listener;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.TransactionEvent;
import com.blockchain.demo.domain.entity.listener.event.TransactionEventConfirmedEvent;
import jakarta.persistence.PostPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class TransactionEventListener {

    private final ApplicationEventPublisher applicationEventPublisher;

    @PostPersist
    private void postPersist(TransactionEvent transactionEvent) {
        if (TransactionEventStatus.CONFIRMED == transactionEvent.getStatus()) {
            this.applicationEventPublisher.publishEvent(new TransactionEventConfirmedEvent(transactionEvent));
        }
    }
}
