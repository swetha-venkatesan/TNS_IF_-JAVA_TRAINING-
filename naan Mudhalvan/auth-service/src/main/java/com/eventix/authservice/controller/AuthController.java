package com.eventix.authservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        // Demo: accept user/password "user"/"password"
        String user = body.get("username");
        String pass = body.get("password");
        if (!"user".equals(user) || !"password".equals(pass)) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        long now = System.currentTimeMillis();
        String jws = Jwts.builder()
            .setSubject(user)
            .claim("roles","USER")
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + jwtExpiration))
            .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
            .compact();
        Map<String,String> resp = new HashMap<>();
        resp.put("token", jws);
        return ResponseEntity.ok(resp);
    }
}
