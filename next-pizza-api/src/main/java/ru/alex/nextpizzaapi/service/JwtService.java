package ru.alex.nextpizzaapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.entity.RefreshToken;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.database.repository.RefreshTokenRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static ru.alex.nextpizzaapi.utils.JwtUtils.setJwtTokenToCookies;

@Service
@Transactional(readOnly = true)
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.subject}")
    private String subject;

    @Value("${app.jwt.issuer}")
    private String issuer;

    @Value("${app.jwt.expiration}")
    @Getter
    private long expirationTime;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpirationTime;

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public JwtService(RefreshTokenRepository refreshTokenRepository,
                      ObjectMapper ignoredObjectMapper) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateAccessToken(String email) throws JWTVerificationException {
        Date expirationDate = Date.from(ZonedDateTime.now().toInstant().plusMillis(expirationTime));
        return JWT.create()
                .withSubject(subject)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    @Transactional
    public void generateRefreshToken(User user) throws JWTVerificationException {
        Date expirationDate = Date.from(ZonedDateTime.now().toInstant().plusMillis(refreshExpirationTime));
        String token = JWT.create()
                .withSubject(subject)
                .withClaim("email", user.getEmail())
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .build();
        refreshTokenRepository.findByUserEmail(user.getEmail())
                .ifPresent(foundToken -> refreshToken.setId(foundToken.getId()));
        refreshTokenRepository.save(refreshToken);
    }


    public String validateTokenAndRetrieveClaim(String token, HttpServletResponse response) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (TokenExpiredException ex) {
            jwt = JWT.decode(token);
            String email = jwt.getClaim("email").asString();
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEmail(email);
            if(refreshToken.isPresent()) {
                verifier.verify(refreshToken.get().getToken());
                String newAccessToken = generateAccessToken(email);
                setJwtTokenToCookies(newAccessToken, response);
            } else {
                throw new JWTVerificationException("refresh token is not valid");
            }
        }
        return jwt.getClaim("email").asString();
    }
}
