package com.wbllwa.util;

import com.wbllwa.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

/**
 * Jwt工具类
 * @author libw
 * @since 2022/12/1 11:07
 */
@Component
public class JwtUtil
{
    /**
     * token超时时间 12h
     */
    private long expiration = 12 * 60 * 60 * 1000;

    /**
     * 生成token的秘钥
     */
    private String base64Security = "abcdefgh";

    public static final String BEARER = "Bearer ";
    public static final String HEADER_KEY = "Authorization";

    private final String CLAIMS_KEY_USERNAME = "sub";
    private final String CLAIMS_KEY_CREATED = "created";

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public String generateToken(LoginUser loginUser)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_KEY_USERNAME, loginUser.getUser().getId());
        claims.put(CLAIMS_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(signatureAlgorithm, base64Security)
                .compact();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUserIdFromToken(String token)
    {
        Claims claims = getClaimsFromToken(token);
        return (String)claims.get(CLAIMS_KEY_USERNAME);
    }

    private Claims getClaimsFromToken(String token)
    {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成过期时间
     * @return
     */
    private Date generateExpirationDate()
    {
        return new Date(System.currentTimeMillis() + expiration);
    }
}
