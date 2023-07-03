package com.blockchain.demo.domain.service;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.entity.Wallet;
import com.blockchain.demo.dto.WalletDto;
import com.blockchain.demo.repository.WalletRepository;
import com.blockchain.demo.repository.mapper.WalletMapper;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public boolean existsByMemberId(Long memberId) {
        return this.walletRepository.existsByMemberId(memberId);
    }

    public WalletDto save(final Long memberId,
                          final String path,
                          final String password,
                          final String address) {
        Wallet wallet = this.walletRepository.save(
            WalletMapper.INSTANCE.of(memberId, path, password, address)
        );

        return WalletMapper.INSTANCE.toDto(wallet);
    }

    public boolean existsByAddress(String address) {
        return this.walletRepository.existsByAddress(address);
    }

    public WalletDto findByMemberId(Long memberId) {
        return this.walletRepository.findByMemberId(memberId)
            .map(WalletMapper.INSTANCE::toDto)
            .orElseThrow(() -> new ErrorCodeException(ErrorCode.NOT_FOUND_WALLET));
    }

    private Wallet findByAddress(String address) {
        return this.walletRepository.findByAddress(address)
            .orElseThrow(() -> new ErrorCodeException(ErrorCode.NOT_FOUND_WALLET));
    }

    public void minusAmount(String address,
                            final BigDecimal amount) {
        Wallet wallet = this.findByAddress(address);
        wallet.changeAmount(wallet.getAmount().subtract(amount));
    }

    public void plusAmount(String address,
                           final BigDecimal amount) {
        Wallet wallet = this.findByAddress(address);
        wallet.changeAmount(wallet.getAmount().add(amount));
    }
}
