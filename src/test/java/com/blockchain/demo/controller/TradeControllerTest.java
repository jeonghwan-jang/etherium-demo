package com.blockchain.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.block.demo.dto.TransactionEventDtoFactory;
import com.block.demo.dto.request.WithdrawRequestDtoFactory;
import com.block.demo.dto.response.WithdrawResponseDtoFactory;
import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.controller.base.BaseControllerTest;
import com.blockchain.demo.service.TradeManagementService;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(TradeController.class)
public class TradeControllerTest extends BaseControllerTest {

    @MockBean
    private TradeManagementService tradeManagementService;

    @Test
    @DisplayName("출금")
    void withdraw() throws Exception {
        given(this.tradeManagementService.withdraw(any(), any())).willReturn(WithdrawResponseDtoFactory.generate());

        this.mockMvc.perform(
            post("/api/v1/trades/withdraw")
                .with(csrf())
                .content(this.objectMapper.writeValueAsString(WithdrawRequestDtoFactory.generate()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcRestDocumentation.document(
                "withdraw",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("to").type(JsonFieldType.STRING).description("보낼 상대방 주소"),
                    fieldWithPath("amount").type(JsonFieldType.NUMBER).description("보낼 금액")
                ),
                responseFields(
                    fieldWithPath("hash").type(JsonFieldType.STRING).description("트랜잭션 해시"),
                    fieldWithPath("from").type(JsonFieldType.STRING).description("보낸 주소"),
                    fieldWithPath("to").type(JsonFieldType.STRING).description("받는 주소"),
                    fieldWithPath("amount").type(JsonFieldType.NUMBER).description("보낸 금액 (Ether)")
                )
            )
        );
    }

    @Test
    @DisplayName("내 거래내역 조회 ")
    void getWallet() throws Exception {
        given(this.tradeManagementService.getTradeHistories(any(), any())).willReturn(
            Collections.singletonList(TransactionEventDtoFactory.generate()));

        this.mockMvc.perform(
            get("/api/v1/trades/my")
        ).andDo(
            MockMvcRestDocumentation.document(
                "get-trade-histories",
                getDocumentRequest(),
                getDocumentResponse(),
                queryParameters(
                    parameterWithName("startingAfter").description("이후로 조회할 ID").optional(),
                    parameterWithName("endingBefore").description("이전으로 조회할 ID").optional(),
                    parameterWithName("size").description("페이지 크기 (max: 100)").optional()
                ),
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("이벤트 ID"),
                    fieldWithPath("[].blockNumber").type(JsonFieldType.NUMBER).description("블록 번호"),
                    fieldWithPath("[].fromAddress").type(JsonFieldType.STRING).description("보낸 주소"),
                    fieldWithPath("[].toAddress").type(JsonFieldType.STRING).description("받는 주소"),
                    fieldWithPath("[].hash").type(JsonFieldType.STRING).description("트랜잭션 해시"),
                    fieldWithPath("[].amount").type(JsonFieldType.NUMBER).description("보낸 금액 (Ether)"),
                    fieldWithPath("[].gasPrice").type(JsonFieldType.NUMBER).description("거래에 사용된 가스 단위당 비용 (Ether)"),
                    fieldWithPath("[].translationFee").type(JsonFieldType.NUMBER).description("거래 수수료 (Ether)"),
                    fieldWithPath("[].blockConfirmation").type(JsonFieldType.NUMBER).description("채굴된 이후 추가된 블록 수"),
                    fieldWithPath("[].status").type(JsonFieldType.STRING)
                        .description(String.format("상태 (%s)", Arrays.stream(
                            TransactionEventStatus.values()).map(Enum::name).collect(Collectors.joining("|")))),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("가입일"),
                    fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("수정일").optional()
                )
            )
        );
    }

}
