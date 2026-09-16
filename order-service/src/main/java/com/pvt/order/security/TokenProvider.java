package com.pvt.order.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final RSAPrivateKey rsaPrivateKey;

    @Value("${spring.jwt.access-token-validity-seconds}")
    private int accessTokenLifeTime;

    @Value("${spring.jwt.refresh-token-validity-seconds}")
    private int refreshTokenLifeTime;

    public String createAccessToken(UUID id, List<String> authorities, boolean loginVerify){
        var now = Instant.now();
        var exp = now.plusSeconds(accessTokenLifeTime);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(id.toString())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(exp))
                .claim("authorities", authorities)
                .claim("login_verify", loginVerify)
                .claim("token_type", "access_token")
                .build();
        return signToken(claims);

    }

    public String createRefreshToken(UUID id){
        var now = Instant.now();
        var exp = now.plusSeconds(refreshTokenLifeTime);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(id.toString())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(exp))
                .claim("token_type", "refresh_token")
                .build();
        return signToken(claims);
    }

    private String signToken(JWTClaimsSet claims){
        try {
            SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), claims);
            signedJWT.sign(new RSASSASigner(rsaPrivateKey));
            return signedJWT.serialize();
        } catch (JOSEException e){
            throw new RuntimeException("ky token that bai", e);
        }
    }
}
