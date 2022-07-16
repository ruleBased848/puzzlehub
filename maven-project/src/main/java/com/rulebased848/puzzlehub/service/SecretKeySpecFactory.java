package com.rulebased848.puzzlehub.service;

import java.security.*;
import javax.crypto.spec.*;
import org.springframework.beans.factory.annotation.*;
import io.jsonwebtoken.*;

public class SecretKeySpecFactory implements KeyFactory {
    @Value("${jwt.secret}")
    private static String key;

    public Key getKey(SignatureAlgorithm alg) {
        return new SecretKeySpec(key.getBytes(), alg.getJcaName());
    }
}