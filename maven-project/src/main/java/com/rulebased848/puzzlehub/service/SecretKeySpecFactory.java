package com.rulebased848.puzzlehub.service;

public class SecretKeySpecFactory implements KeyFactory {
    private String key;

    SecretKeySpecFactory() {
        var p = new java.util.Properties();
        try {
            p.load(new java.io.FileReader("src/main/resources/application.properties"));
        } catch (java.io.IOException e) {
            key = null;
            return;
        }
        key = p.getProperty("jwt.secret");
    }

    public java.security.Key getKey(io.jsonwebtoken.SignatureAlgorithm alg) {
        return new javax.crypto.spec.SecretKeySpec(key.getBytes(), alg.getJcaName());
    }
}