package com.rulebased848.puzzlehub;

import org.springframework.context.annotation.*;
import org.springframework.security.web.*;
import org.springframework.security.config.annotation.web.builders.*;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.build();
    }
}