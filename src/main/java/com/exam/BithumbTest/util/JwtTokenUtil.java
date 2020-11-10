package com.exam.BithumbTest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor
@Getter
public class JwtTokenUtil {
    @Value("${jwt.secret_key}")
    private  String SECRET_KEY;
    @Value("${jwt.token_expire}")
    private long JWT_TOKEN_VALIDITY;
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer";

    // token에서 userId(email) 조회
    public  String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private  Claims getAllClaimsFromToken(String token) throws MalformedJwtException {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).getBody();
    }
    // token 만료여부
    private  Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private  Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public  String generateToken(String id) {
        return doGenerateToken(id, createClaims(id));
    }

    // public claim에 이메일을 설정하여 정보를 조회
    private  Map<String, Object> createClaims(String id) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", id);

        return claims;
    }

    // jwt token 생성
    private  String doGenerateToken(String id, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setHeader(createHeader())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, createSigningKey())
                .compact();
    }

    // 서명부분 HS512 알고리즘 사용
    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }
    // token 검증
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getEmailFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    // header 생성(type, algorithm)
    private  Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS512");

        return header;
    }
}
