package com.chaoui.artico.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(String secretKey,
                            Duration accessTokenExpiration,
                            Duration refreshTokenExpiration) {
}
