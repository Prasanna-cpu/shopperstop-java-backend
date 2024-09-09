package com.kumar.shopperstop.Security.jwt;

import com.kumar.shopperstop.Security.User.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {


    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationMillis}")
    private int expirationTime;

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateJwtToken(Authentication authentication) {

        ShopUserDetails userPrincipal=(ShopUserDetails) authentication.getPrincipal();

        List<String> roles=userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toUnmodifiableList());

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id",userPrincipal.getId())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+expirationTime))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromToken(String jwt){

        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String jwt){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt);
            return true;
        }
        catch(ExpiredJwtException | UnsupportedJwtException |MalformedJwtException  | IllegalArgumentException e){
            throw  new JwtException(e.getMessage());
        }
    }


}


