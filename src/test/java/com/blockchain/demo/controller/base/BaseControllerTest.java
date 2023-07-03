package com.blockchain.demo.controller.base;

import com.blockchain.demo.domain.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WithMockUser
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    protected static OperationRequestPreprocessor getDocumentRequest() {

        return Preprocessors.preprocessRequest(
            Preprocessors.modifyUris(),
            Preprocessors.prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
    }
}
