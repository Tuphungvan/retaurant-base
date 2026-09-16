package com.pvt.order.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        if(jwt.getSubject() != null) throw new JwtException("jwt khong duoc thieu subject");
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt){
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        if(authorities == null) return Collections.emptyList();
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
