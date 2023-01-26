package org.signing237.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
public class JwtUtil {


    public Map<String, String> generateToken(User customer) {
        Map<String ,String> token = new HashMap<>();
        token.put("access_token",accessToken(customer));
        token.put("refresh_token",refreshToken(customer));
        return token;
    }

    private String refreshToken(User customer) {
        return JWT.create()
                .withSubject(customer.getUsername())
                .withIssuer("www.QuestionStack.com")
                .withClaim("roles",getClaims(customer))
                .withIssuedAt(Instant.now())
                .sign(getAlgorithm());
    }

    private String accessToken(User customer) {
        return JWT.create()
                .withSubject(customer.getUsername())
                .withIssuer("www.QuestionStack.com")
                .withClaim("roles",getClaims(customer))
                .withIssuedAt(Instant.now())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
                .sign(getAlgorithm());
    }

    private List<String> getClaims(User customer) {
       return customer.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256("hello");
    }


    public Collection<? extends GrantedAuthority> getAuthorities(Claim roles) {
        Collection<SimpleGrantedAuthority> authorities  =new ArrayList<>();
        roles.asList(String.class).forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    public DecodedJWT decoderToken(String token){
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }
}
