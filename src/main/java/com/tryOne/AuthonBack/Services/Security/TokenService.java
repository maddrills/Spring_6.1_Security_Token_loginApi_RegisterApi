package com.tryOne.AuthonBack.Services.Security;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    //authentication must be from org.spring.security
    public String generateJwt(Authentication auth){

        //snapshot of the time to mark the start of a token
        Instant now = Instant.now();

        //1:24:10
        //going to loop through all the authorities in auth, auth has all the roles from the user
        String scope = auth.getAuthorities().stream()
                //if you check Role you can c we have implemented GrantedAuthority
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        System.out.println(scope);

        //the signature
        JwtClaimsSet claims = JwtClaimsSet.builder()
                //this specific service is issuing the service
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .claim("roles",scope)
                .build();
        // 1:44:00 check main class for video
        //using the JwtEncoder to encode a new jwt token we are getting the encoding info from the jwt parameters witch has self now auth.getName roles build
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
