package SpringBoot.Billwise.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "BillWiseSecretKeyForJwtAuthentication2026VerySecureKey";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour

    // Create Secret Key
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Generate JWT
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    // Extract email
    public String extractEmail(String token) {

        return getClaims(token).getSubject();
    }

    // Validate JWT
    public boolean isTokenValid(String token) {

        try {

            getClaims(token);

            return true;

        } catch (Exception e) {

            System.out.println("JWT ERROR: " + e.getMessage());

            return false;
        }
    }

    // Get claims
    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}