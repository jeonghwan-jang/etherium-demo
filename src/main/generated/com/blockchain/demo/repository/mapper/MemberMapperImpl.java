package com.blockchain.demo.repository.mapper;

import com.blockchain.demo.domain.entity.Member;
import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.response.MemberResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-02T11:26:48+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member of(String username, String password, String name, List<String> roles) {
        if ( username == null && password == null && name == null && roles == null ) {
            return null;
        }

        String username1 = null;
        username1 = username;
        String password1 = null;
        password1 = password;
        String name1 = null;
        name1 = name;
        List<String> roles1 = null;
        List<String> list = roles;
        if ( list != null ) {
            roles1 = new ArrayList<String>( list );
        }

        Long id = null;
        LocalDateTime lastLoginDt = null;

        Member member = new Member( id, username1, password1, name1, lastLoginDt, roles1 );

        return member;
    }

    @Override
    public MemberResponseDto toResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        Long id = null;
        String username = null;
        String name = null;
        LocalDateTime lastLoginDt = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        List<String> roles = null;

        id = member.getId();
        username = member.getUsername();
        name = member.getName();
        lastLoginDt = member.getLastLoginDt();
        createdAt = member.getCreatedAt();
        updatedAt = member.getUpdatedAt();
        List<String> list = member.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }

        MemberResponseDto memberResponseDto = new MemberResponseDto( id, username, name, lastLoginDt, createdAt, updatedAt, roles );

        return memberResponseDto;
    }

    @Override
    public LoggedMemberDto toDto(Member member) {
        if ( member == null ) {
            return null;
        }

        Long id = null;
        String username = null;
        String name = null;
        List<String> roles = null;

        id = member.getId();
        username = member.getUsername();
        name = member.getName();
        List<String> list = member.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }

        LoggedMemberDto loggedMemberDto = new LoggedMemberDto( id, username, name, roles );

        return loggedMemberDto;
    }
}
