package net.unibave.avaapi.security;

import net.unibave.avaapi.entities.User;
import net.unibave.avaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = authentication.getPrincipal().toString();
        final String password = authentication.getCredentials().toString();


        try {
            final User user = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }

            return new UsernamePasswordAuthenticationToken(email, null, user.getRoles());

        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect username or password", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
