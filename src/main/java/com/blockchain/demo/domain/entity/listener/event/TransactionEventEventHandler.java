package com.blockchain.demo.domain.entity.listener.event;

import com.blockchain.demo.domain.entity.TransactionEvent;
import com.blockchain.demo.domain.service.TradeRequestHistoryService;
import com.blockchain.demo.domain.service.WalletService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
class TransactionEventEventHandler {

    private final WalletService walletService;

    private final TradeRequestHistoryService tradeRequestHistoryService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = TransactionEventConfirmedEvent.class)
    public void onTransactionEventConfirmedEvent(TransactionEventConfirmedEvent event) {
        TransactionEvent transactionEvent = event.transactionEvent();
        BigDecimal amount = transactionEvent.getAmount();

        String from = transactionEvent.getFromAddress();
        if (this.walletService.existsByAddress(from)) {
            this.walletService.minusAmount(from, amount.add(transactionEvent.getTranslationFee()));
            this.tradeRequestHistoryService.deleteByHash(transactionEvent.getHash());
        }

        String to = transactionEvent.getToAddress();
        if (this.walletService.existsByAddress(to)) {
            this.walletService.plusAmount(to, amount);
        }
    }
}
