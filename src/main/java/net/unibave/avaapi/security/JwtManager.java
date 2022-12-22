package net.unibave.avaapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.unibave.avaapi.entities.User;
import net.unibave.avaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtManager {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    private final UserService userService;

    @Autowired
    public JwtManager(UserService userService) {
        this.userService = userService;
    }

    public String generateJwtToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        boolean isTokenExpired = claims.getExpiration().before(new Date());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getEmail(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
