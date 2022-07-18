package com.rulebased848.puzzlehub.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

@org.springframework.web.bind.annotation.RestController
public class LoginController {
    @Autowired
    private com.rulebased848.puzzlehub.service.JwtService jwtService;
    @Autowired
    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody com.rulebased848.puzzlehub.domain.AuthRequest req) {
        var cred = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        var auth = authenticationManager.authenticate(cred);
        var jwt = jwtService.getToken(auth.getName());
        return ResponseEntity
            .ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
            .build();
    }
}