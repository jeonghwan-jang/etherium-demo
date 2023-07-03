package com.blockchain.demo.resolver;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.service.MemberService;
import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.security.annotation.LoggedMember;
import java.lang.annotation.Annotation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoggedMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        LoggedMember annotation = this.findMethodAnnotation(LoggedMember.class, parameter);
        LoggedMemberDto memberDto = null;
        try {
            memberDto = this.memberService.getLoginUserInfo();
        } catch (ErrorCodeException e) {
            if (!annotation.nullable()) {
                throw e;
            }
        }

        return memberDto;
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass,
                                                          MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }

        throw new ErrorCodeException(ErrorCode.SERVER_ERROR);
    }
}