package com.blockchain.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.block.demo.dto.JwtTokenDtoFactory;
import com.block.demo.dto.request.LoginRequestDtoFactory;
import com.block.demo.dto.request.MemberRequestDtoFactory;
import com.block.demo.dto.response.MemberResponseDtoFactory;
import com.blockchain.demo.controller.base.BaseControllerTest;
import com.blockchain.demo.service.MemberManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends BaseControllerTest {

    @MockBean
    private MemberManagementService memberManagementService;

    @Test
    @DisplayName("회원가입")
    void generateMember() throws Exception {
        given(this.memberManagementService.generateMember(any())).willReturn(MemberResponseDtoFactory.generate());

        this.mockMvc.perform(
            post("/api/v1/members")
                .with(csrf())
                .content(this.objectMapper.writeValueAsString(MemberRequestDtoFactory.generate()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcRestDocumentation.document(
                "generate-member",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 ID"),
                    fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("lastLoginDt").type(JsonFieldType.STRING).description("마지막 로그인 일시").optional(),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("가입일"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일").optional(),
                    fieldWithPath("roles").type(JsonFieldType.ARRAY).description("권한")
                )
            )
        );
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        given(this.memberManagementService.login(any())).willReturn(JwtTokenDtoFactory.generate());

        this.mockMvc.perform(
            post("/api/v1/members/login")
                .with(csrf())
                .content(this.objectMapper.writeValueAsString(LoginRequestDtoFactory.generate()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcRestDocumentation.document(
                "login",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                ),
                responseFields(
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("Access Token"),
                    fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("Refresh Token")
                )
            )
        );
    }

}
