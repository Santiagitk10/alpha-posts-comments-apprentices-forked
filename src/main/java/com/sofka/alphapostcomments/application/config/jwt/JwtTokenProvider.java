package com.sofka.alphapostcomments.application.config.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY="roles";
    private final JwtProvider jwtProvider;
    private SecretKey secretKey;

    @PostConstruct
    protected void init(){
        var secret = Base64.getEncoder().encodeToString(jwtProvider.getSecretKey().getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication){
        var userName = authentication.getName();
        var authorities = authentication.getAuthorities();

        var claims = Jwts.claims().setSubject(userName);

        //Creates an entry in the claims set with roles:List of permissions
        if(!authorities.isEmpty()){
            claims.put(AUTHORITIES_KEY, authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(",")));


        }

        //Setting validity time of the ti
        var currentDate = new Date();
        var validityDate = new Date(currentDate.getTime() + this.jwtProvider.getTokenValidity());

        //Generating the token
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(validityDate)
                .signWith(this.secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    //TODO I know this method takes the token and return and Authentication
    //TODO docs say An Authentication implementation that is designed for simple presentation of a username and password.
    //TODO An authentication is just a username and Password?
    public Authentication getAuthentication(String token){

        //From Token prepare Claim to be authenticated
        var claims = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        var claimAuthorities = claims.get(AUTHORITIES_KEY);

        var authorities = claimAuthorities != null
                ? AuthorityUtils.commaSeparatedStringToAuthorityList(claimAuthorities.toString())
                : AuthorityUtils.NO_AUTHORITIES;

        var principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);


    }


    public boolean validateToken(String token){
        //call the claims
        try{
            var claims = Jwts.parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build().parseClaimsJws(token);

            //TODO NO FALTABA ESTE PARA LA VALIDACIÓN DE TIEMPO? SIEMPRE ESTABA DANDO TRUE
           /* if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }*/

            log.info("Token is ok: Expiration time is " + claims.getBody().getExpiration().toString());
            return true;
        }catch(JwtException | IllegalArgumentException e){
            log.info("Invalid token: " + e.getMessage());
        }
        return false;

    }




}
