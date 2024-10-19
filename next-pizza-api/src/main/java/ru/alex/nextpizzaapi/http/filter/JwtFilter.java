package ru.alex.nextpizzaapi.http.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alex.nextpizzaapi.dto.error.ErrorResponse;
import ru.alex.nextpizzaapi.service.JwtService;
import ru.alex.nextpizzaapi.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public JwtFilter(JwtService jwtService,
                     UserService userService,
                     ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = Optional.ofNullable(request.getCookies())
                    .stream()
                    .flatMap(Arrays::stream)
                    .filter(c -> c.getName().equals("jwt-token"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElseThrow(() -> new JWTVerificationException("JWT token not found in cookies"));
            if(token == null || token.isBlank()) {
                throw new JWTVerificationException("Invalid JWT token in cookies");
            } else {
                String email = jwtService.validateTokenAndRetrieveClaim(token, response);
                UserDetails userDetails = userService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request, response);
            }
        } catch (JWTVerificationException ex) {
            setErrorResponse(BAD_REQUEST, response, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api");
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
        response.setStatus(status.value());
        response.setContentType("application/json");

        ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                ex.getMessage()
        );
        try (PrintWriter writer = response.getWriter()) {
            String json = objectMapper.writeValueAsString(errorResponse);
            if (writer != null) {
                writer.write(json);
            } else {
                response.getOutputStream().write(json.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
