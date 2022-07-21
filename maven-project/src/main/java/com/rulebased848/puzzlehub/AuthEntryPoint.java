package com.rulebased848.puzzlehub;

import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Component
public class AuthEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    public void commence(
        javax.servlet.http.HttpServletRequest request,
        HttpServletResponse response,
        org.springframework.security.core.AuthenticationException authException
    ) throws java.io.IOException, javax.servlet.ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        var writer = response.getWriter();
        writer.println("Error: " + authException.getMessage());
    }
}