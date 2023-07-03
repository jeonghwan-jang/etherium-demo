package com.blockchain.demo.repository.mapper;

import com.blockchain.demo.domain.entity.Member;
import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.MemberDto;
import com.blockchain.demo.dto.response.MemberResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member of(String username,
              String password,
              String name,
              List<String> roles);

    MemberDto toDto(Member member);

    LoggedMemberDto toLoggedDto(MemberDto memberDto);

    MemberResponseDto toResponseDto(MemberDto memberDto);
}
