package net.unibave.avaapi.controllers;

import net.unibave.avaapi.models.auth.Credentials;
import net.unibave.avaapi.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtAuthenticationProvider authenticationProvider;

    @Autowired
    public AuthController(JwtAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Credentials credentials) {
        return ResponseEntity.ok(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())));
    }
}
