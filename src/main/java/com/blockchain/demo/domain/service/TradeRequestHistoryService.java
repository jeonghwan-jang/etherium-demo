package com.blockchain.demo.domain.service;

import com.blockchain.demo.domain.entity.TradeRequestHistory;
import com.blockchain.demo.repository.TradeRequestHistoryRepository;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
@Service
public class TradeRequestHistoryService {

    private final TradeRequestHistoryRepository tradeRequestHistoryRepository;

    public BigDecimal getTotalRequestAmountByMemberId(Long memberId) {
        BigDecimal totalRequestAmount = this.tradeRequestHistoryRepository.getTotalRequestAmountByMemberId(memberId);
        return Objects.isNull(totalRequestAmount) ? BigDecimal.ZERO : totalRequestAmount;
    }

    public void save(final Long memberId,
                     final String hash,
                     final String from,
                     final String to,
                     final BigDecimal amount) {
        this.tradeRequestHistoryRepository.save(
            TradeRequestHistory.builder()
                .memberId(memberId)
                .hash(hash)
                .fromAddress(from)
                .toAddress(to)
                .amount(amount)
                .build()
        );
    }

    public void deleteByHash(String hash) {
        this.tradeRequestHistoryRepository.deleteByHash(hash);
    }
}
