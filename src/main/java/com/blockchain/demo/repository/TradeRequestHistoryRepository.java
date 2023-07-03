package com.blockchain.demo.repository;

import com.blockchain.demo.domain.entity.TradeRequestHistory;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRequestHistoryRepository extends JpaRepository<TradeRequestHistory, Long> {

    @Query("SELECT SUM(h.amount) FROM TradeRequestHistory h WHERE h.memberId = :memberId")
    BigDecimal getTotalRequestAmountByMemberId(Long memberId);

    void deleteByHash(String hash);
}
