package com.twitter.services;

import com.nimbusds.jwt.JWTClaimsSet;
import com.twitter.controllers.AuthenticationController;
import lombok.AllArgsConstructor;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    @Autowired
    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication auth){
        Instant now = Instant.now();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        Date dateFromInstant = Date.from(now);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now) // Pass the java.util.Date object here
                .subject(auth.getName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    public String getUsernameFromToken(String token){

        if(!token.substring(0,6).equals("Bearer")) throw new InvalidBearerTokenException("Token is not a Bearer token");
        String strippedToken = token.substring(7);
        Jwt decoded = jwtDecoder.decode(strippedToken);
        String username = decoded.getSubject();
        return username;
    }
}
