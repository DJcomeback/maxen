package com.gec.system.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

//JWT的生成和解析
public class JwtUtil {
    // 创建token
    public static String createToken(Map claims) {
        return Jwts.builder()
                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))//设置有效时间为1天
                .setClaims(claims) //设置响应数据体
                .signWith(SignatureAlgorithm.HS256, "dafei_movies") //设置加密方法和加密盐
                .compact();
    }

    // 解析token
    public static Map parseToken(String token) {
        return Jwts.parser().setSigningKey("dafei_movies")
                .parseClaimsJws(token)
                .getBody();
    }
}
