package com.wbllwa.util;

import com.wbllwa.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /**
     *
     */
    private final String CLAIMS_KEY_USERNAME = "sub";

    /**
     *
     */
    private final String CLAIMS_KEY_CREATED = "sub";

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
                .signWith(SignatureAlgorithm.HS512, base64Security)
                .compact();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token)
    {
        Claims claims = getClaimsFromToken(token);
        String username = claims.getSubject();
        return username;
    }

    private Claims getClaimsFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(base64Security)
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


    /**
     * 新建token
     * @param audience
     * @param issuer
     * @return
     */
//    public static String createToken(String audience, String issuer)
//    {
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//
//        // 生成签名密钥
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//        // 添加构成JWT的参数
//        JwtBuilder builder = Jwts.builder()
//                .setHeaderParam("typ", "JWT")
//                .setIssuer(issuer)
//                .setAudience(audience)
//                .signWith(signatureAlgorithm, signingKey);
//
//        // 添加Token签发时间
//        builder.setIssuedAt(now);
//        // 添加Token过期时间
//        if (expiration >= 0) {
//            long expMillis = nowMillis + expiration;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp).setNotBefore(now);
//        }
//
//        // 生成JWT
//        return builder.compact();
//    }

    /**
     * 解析token
     * @param jsonWebToken
     * @return
     */
    public Claims parseToken(String jsonWebToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJwt(jsonWebToken)
                .getBody();

        return claims;
    }

    /**
     * 刷新令牌
     *
     * @param claims
     * @return
     */
    public String refreshToken(Claims claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuer((String) claims.get("iss")).setAudience((String) claims.get("aud"))
                .signWith(signatureAlgorithm, signingKey);

        // 添加Token签发时间
        builder.setIssuedAt(now);
        // 添加Token过期时间
        if (expiration >= 0) {
            long expMillis = nowMillis + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // 生成Token
        return builder.compact();
    }
}
