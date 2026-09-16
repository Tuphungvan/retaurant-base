package com.pvt.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtKeyConfiguration {

    @Value("${spring.jwt.private-key}")
    private String privateKey;

    @Value("${spring.jwt.public-key}")
    private String publicKey;

    @Bean
    public RSAPrivateKey rsaPrivateKey(){
        Assert.hasText(privateKey, "private key khong đuoc trong");
        try {
            byte[] decoder = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoder);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("lay private key that bai", e);
        }
    }

    @Bean
    public RSAPublicKey rsaPublicKey(){
        Assert.hasText(privateKey, "public key khong đuoc trong");
        try {
             byte[] decoder = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoder);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("lay public key that bai", e);
        }
    }
}
