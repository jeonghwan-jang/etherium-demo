package com.blockchain.demo.repository;

import com.blockchain.demo.domain.entity.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByMemberId(Long memberId);

    boolean existsByAddress(String address);

    Optional<Wallet> findByMemberId(Long memberId);

    Optional<Wallet> findByAddress(String address);
}
