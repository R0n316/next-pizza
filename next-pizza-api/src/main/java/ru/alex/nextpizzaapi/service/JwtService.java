package ru.alex.nextpizzaapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.subject}")
    private String subject;

    @Value("${app.jwt.issuer}")
    private String issuer;

    public String generateToken(String email) throws JWTVerificationException {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(15).toInstant());
        return JWT.create()
                .withSubject(subject)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
