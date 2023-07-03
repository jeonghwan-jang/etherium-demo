package com.blockchain.demo.repository.mapper;

import com.blockchain.demo.domain.entity.Wallet;
import com.blockchain.demo.dto.response.WalletResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-02T11:26:49+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
public class WalletMapperImpl implements WalletMapper {

    @Override
    public Wallet of(Long memberId, String path, String address) {
        if ( memberId == null && path == null && address == null ) {
            return null;
        }

        Long memberId1 = null;
        memberId1 = memberId;
        String path1 = null;
        path1 = path;
        String address1 = null;
        address1 = address;

        Long id = null;
        BigDecimal amount = null;

        Wallet wallet = new Wallet( id, memberId1, path1, address1, amount );

        return wallet;
    }

    @Override
    public WalletResponseDto toResponseDto(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        Long id = null;
        String address = null;
        BigDecimal amount = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = wallet.getId();
        address = wallet.getAddress();
        amount = wallet.getAmount();
        createdAt = wallet.getCreatedAt();
        updatedAt = wallet.getUpdatedAt();

        WalletResponseDto walletResponseDto = new WalletResponseDto( id, address, amount, createdAt, updatedAt );

        return walletResponseDto;
    }
}
