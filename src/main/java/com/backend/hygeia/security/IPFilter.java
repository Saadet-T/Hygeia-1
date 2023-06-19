package com.backend.hygeia.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IPFilter implements Filter {
    private final List<String> allowedIPs = Arrays.asList("127.0.0.1", "192.168.0.1"); // İzin verilen IP listesi

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIP = httpRequest.getHeader("X-Forwarded-For");
        System.out.println("clientIP: "+clientIP);
       	
        if (isAllowedIP(clientIP) || hasRole("ADMIN")) {
            // İzin verilen IP adresi, devam et
            chain.doFilter(request, response);
        } else {
            // İzin verilmeyen IP adresi, 401 Unauthorized yanıtı gönder
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isAllowedIP(String ip) {
        return allowedIPs.contains(ip);
    }
    private boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
        }
        return false;
    }

}
