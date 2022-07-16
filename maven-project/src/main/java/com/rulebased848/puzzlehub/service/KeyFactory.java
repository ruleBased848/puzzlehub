package com.rulebased848.puzzlehub.service;

import java.security.*;
import io.jsonwebtoken.*;

public interface KeyFactory {
    public Key getKey(SignatureAlgorithm alg);
}