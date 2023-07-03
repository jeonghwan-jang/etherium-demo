package com.blockchain.demo.domain.service;

import com.blockchain.demo.domain.entity.BlockNumberHistory;
import com.blockchain.demo.repository.BlockNumberHistoryRepository;
import jakarta.annotation.Nullable;
import java.math.BigInteger;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
@Service
public class BlockNumberHistoryService {

    private final BlockNumberHistoryRepository blockNumberHistoryRepository;

    @Nullable
    public BigInteger getLatestBlockNumber() {
        BlockNumberHistory blockNumberHistory = this.blockNumberHistoryRepository.findTopByOrderByBlockNumberDesc()
            .orElse(null);

        return Objects.isNull(blockNumberHistory) ? null : blockNumberHistory.getBlockNumber();
    }

    public void saveIfNotExists(final BigInteger blockNumber) {
        boolean isNotDuplicated = !this.blockNumberHistoryRepository.existsByBlockNumber(blockNumber);
        if (isNotDuplicated) {
            this.blockNumberHistoryRepository.save(
                new BlockNumberHistory(blockNumber)
            );
        }
    }
}
