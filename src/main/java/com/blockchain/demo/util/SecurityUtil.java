package com.blockchain.demo.util;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {

    public static Object getPrincipal() {
        Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }
        return authentication.getPrincipal();
    }

    private static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.isNull(context)) {
            return null;
        }

        return context.getAuthentication();
    }
}
