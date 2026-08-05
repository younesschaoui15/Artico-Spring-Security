package com.chaoui.artico.security.jwt;

import com.chaoui.artico.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;

    //    @Value("${security.jwt.secret-key}") //Use jwt properties class instead
    private String secretKey;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    @Value("${spring.application.name}")
    private String appName;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = jwtProperties.secretKey();
        this.accessTokenExpiration = jwtProperties.accessTokenExpiration().toMillis();
        this.refreshTokenExpiration = jwtProperties.refreshTokenExpiration().toMillis();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        claims.put("type", TokenType.ACCESS.name());
        claims.put("roles", roles);

        return buildToken(userDetails, claims, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("type", TokenType.REFRESH.name());

        return buildToken(userDetails, claims, refreshTokenExpiration);
    }

    // to add extra claims (e.g., roles) to the token
    private String buildToken(UserDetails userDetails,
                              Map<String, Object> claims,
                              long tokenExpiration) {
        return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuer(appName)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(getSigningKey())
            .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith((SecretKey) getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean isAccessToken(String token) {
        String tokenType = extractAllClaims(token).get("type", String.class);
        System.out.println("# Token Type: " + tokenType);

        return TokenType.ACCESS.name().equals(tokenType);
    }

    public boolean isRefreshToken(String token) {
        String tokenType = extractAllClaims(token).get("type", String.class);
        System.out.println("# Token Type: " + tokenType);

        return TokenType.REFRESH.name().equals(tokenType);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String authUsername = userDetails.getUsername();
            String requestUsername = extractUsername(token);
            System.out.println("# Request Username : " + requestUsername + " - Auth Username: " + authUsername);

            return authUsername.equals(requestUsername) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isHeaderFormatValid(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }
}
