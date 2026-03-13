package com.bangladesh20.backend.Security;

import com.bangladesh20.backend.Entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerate {

    @Value("${jwt.secrectkey}")
    private String secrectKey;

    private SecretKey getSecrectKey(){
        return Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));
    }

    public String JwtTokenGenerate(Users users)
    {
        return Jwts.builder()
                .subject(users.getUsername())
                .claim("userId",users.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSecrectKey())
                .compact();
    }

}
