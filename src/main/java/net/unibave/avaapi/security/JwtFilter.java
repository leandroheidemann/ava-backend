package net.unibave.avaapi.security;

import net.unibave.avaapi.entities.User;
import net.unibave.avaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final UserService userService;

    @Autowired
    public JwtFilter(JwtManager jwtManager, UserService userService) {
        this.jwtManager = jwtManager;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenOfRequest(request);

        if (token != null && !token.isEmpty()) {
            try {
                String email = jwtManager.getEmail(token);
                User user = userService.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                final var authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getRoles());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenOfRequest(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");

        if (!(tokenHeader != null && tokenHeader.startsWith("Bearer "))) {
            return null;
        }

        return tokenHeader.replace("Bearer ", "");
    }
}
