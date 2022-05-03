package com.csuft.uc.utils;

import com.csuft.common.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    //单位是毫秒
    private static long ttl = Constants.Millions.TWO_HOUR;//2个小时


    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * @param claims 载荷内容
     * @param ttl    有效时长
     * @return
     */
    public static String createToken(Map<String, Object> claims, long ttl,String salt) {
        JwtUtil.ttl = ttl;
        return createToken(claims,salt);
    }


    public static String createRefreshToken(String userId, long ttl,String salt) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, salt);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * @param claims 载荷
     * @return token
     */
    public static String createToken(Map<String, Object> claims,String salt) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, salt);

        if (claims != null) {
            builder.setClaims(claims);
        }

        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwtStr, String salt) {
        return Jwts.parser()
                .setSigningKey(salt)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}