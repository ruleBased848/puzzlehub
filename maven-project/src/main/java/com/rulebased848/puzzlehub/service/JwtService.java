package com.rulebased848.puzzlehub.service;

@org.springframework.stereotype.Component
public class JwtService {
    private static final long expirationTime = 24 * 60 * 60 * 1000;
    private static final String prefix = "Bearer";
    private static final java.security.Key key = new SecretKeySpecFactory()
        .getKey(io.jsonwebtoken.SignatureAlgorithm.HS256);

    public String getToken(String username) {
        return io.jsonwebtoken.Jwts
            .builder()
            .setSubject(username)
            .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationTime))
            .signWith(key)
            .compact();
    }

    public String getAuthUser(javax.servlet.http.HttpServletRequest request) {
        var token = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
        return token == null ? null : io.jsonwebtoken.Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token.replace(prefix, ""))
            .getBody()
            .getSubject();
    }
}