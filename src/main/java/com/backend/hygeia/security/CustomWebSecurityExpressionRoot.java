package com.backend.hygeia.security;

import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import javax.servlet.http.HttpServletRequest;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot implements SecurityExpressionOperations {

    private final HttpServletRequest request;

    public CustomWebSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation) {
        super(authentication, filterInvocation);
        this.request = filterInvocation.getRequest();
    }

    public boolean hasIpAddress(String ipAddress) {
        return request.getRemoteAddr().equals(ipAddress);
    }
}

