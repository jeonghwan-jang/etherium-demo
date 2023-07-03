package com.blockchain.demo.repository;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.TransactionEvent;
import com.blockchain.demo.dto.request.PagingRequestDto;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEventRepository extends JpaRepository<TransactionEvent, Long> {

    List<TransactionEvent> getOnlyMinedStatus();

    boolean existsByHashAndStatus(String hash,
                                  TransactionEventStatus status);

    Stream<TransactionEvent> getTransactionEvents(String address,
                                                  PagingRequestDto requestDto);
}
