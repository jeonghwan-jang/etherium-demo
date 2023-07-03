package com.blockchain.demo.repository;

import com.blockchain.demo.domain.entity.BlockNumberHistory;
import java.math.BigInteger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockNumberHistoryRepository extends JpaRepository<BlockNumberHistory, Long> {

    Optional<BlockNumberHistory> findTopByOrderByBlockNumberDesc();

    boolean existsByBlockNumber(BigInteger blockNumber);
}
