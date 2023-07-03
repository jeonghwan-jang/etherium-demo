package com.blockchain.demo.repository.mapper;

import com.blockchain.demo.domain.entity.Wallet;
import com.blockchain.demo.dto.WalletDto;
import com.blockchain.demo.dto.response.WalletResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    Wallet of(Long memberId,
              String path,
              String password,
              String address);

    WalletDto toDto(Wallet wallet);

    WalletResponseDto toResponseDto(WalletDto walletDto);
}
