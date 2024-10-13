package ru.alex.nextpizzaapi.http.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alex.nextpizzaapi.service.JwtService;
import ru.alex.nextpizzaapi.service.UserService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtFilter(JwtService jwtService,
                     UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        String authHeader = request.getHeader("authorization");
        if(authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid JWT token in Bearer Header"
            );
        } else {
            String jwt = authHeader.substring(7);

            if(jwt.isBlank()) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT token in Bearer Header"
                );
            } else {
                try {
                    String email = jwtService.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = userService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );

                    if(SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException ex) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT token"
                    );
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/auth");
    }
}
