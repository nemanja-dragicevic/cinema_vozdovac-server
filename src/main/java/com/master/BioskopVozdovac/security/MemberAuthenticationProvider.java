package com.master.BioskopVozdovac.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class MemberAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final MemberService memberService;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(MemberDTO dto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour valid

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(dto.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("role", dto.getRole().name())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        MemberDTO dto = memberService.findByUsername(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(dto, null, Arrays.asList(dto.getRole()));
    }

}
