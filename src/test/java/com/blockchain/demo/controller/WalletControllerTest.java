package com.blockchain.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.block.demo.dto.request.WalletRequestDtoFactory;
import com.block.demo.dto.response.WalletResponseDtoFactory;
import com.blockchain.demo.controller.base.BaseControllerTest;
import com.blockchain.demo.service.WalletManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(WalletController.class)
public class WalletControllerTest extends BaseControllerTest {

    @MockBean
    private WalletManagementService walletManagementService;

    @Test
    @DisplayName("지갑 생성")
    void generateMember() throws Exception {
        given(this.walletManagementService.generateWallet(any(), any())).willReturn(
            WalletResponseDtoFactory.generate());

        this.mockMvc.perform(
            post("/api/v1/wallets")
                .with(csrf())
                .content(this.objectMapper.writeValueAsString(WalletRequestDtoFactory.generate()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcRestDocumentation.document(
                "generate-wallet",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("password").type(JsonFieldType.STRING).description("지갑 비밀번호")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("지갑 ID"),
                    fieldWithPath("address").type(JsonFieldType.STRING).description("지갑 주소"),
                    fieldWithPath("amount").type(JsonFieldType.NUMBER).description("금액"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("가입일"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일").optional()
                )
            )
        );
    }

    @Test
    @DisplayName("내 지갑 조회")
    void getWallet() throws Exception {
        given(this.walletManagementService.getWallet(any())).willReturn(WalletResponseDtoFactory.generate());

        this.mockMvc.perform(
            get("/api/v1/wallets/my")
        ).andDo(
            MockMvcRestDocumentation.document(
                "get-wallet",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("지갑 ID"),
                    fieldWithPath("address").type(JsonFieldType.STRING).description("지갑 주소"),
                    fieldWithPath("amount").type(JsonFieldType.NUMBER).description("금액"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("가입일"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일").optional()
                )
            )
        );
    }

}
