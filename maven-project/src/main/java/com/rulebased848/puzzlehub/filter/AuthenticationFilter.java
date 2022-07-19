package com.rulebased848.puzzlehub.filter;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component
public class AuthenticationFilter extends org.springframework.web.filter.OncePerRequestFilter {
    @Autowired
    private com.rulebased848.puzzlehub.service.JwtService jwtService;

    protected void doFilterInternal(
        javax.servlet.http.HttpServletRequest request,
        javax.servlet.http.HttpServletResponse response,
        javax.servlet.FilterChain filterChain
    ) throws javax.servlet.ServletException, java.io.IOException {
        var user = jwtService.getAuthUser(request);
        if (user != null) {
            var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());
            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}