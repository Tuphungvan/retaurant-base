package com.pvt.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtSecurityConfiguration {
    @Bean
    public JwtDecoder decoder(RSAPublicKey rsaPublicKey){
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }
}
