package org.chuan.woj;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/16 9:14
 * @Description:
 */
@SpringBootTest
@Slf4j
public class JwtTest {

    String secret = "vaiusd981b2jjkb53j24b5jkbiu7t57y6sdctvg1j4b";
    @Test
    public void test(){

        Map<String,Object> map = new HashMap<>();
        map.put("uuid","123123123");
        map.put("userAccount","chuan");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,secret) // 签名算法
                .setClaims(map) //自定义内容
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000)) //有效期1h
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void Test2( ) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyQWNjb3VudCI6ImNodWFuIiwidXVpZCI6IjEyMzEyMzEyMyIsImV4cCI6MTcyNDAzNDA5NH0.SxyTpMQEar6gV4xXz-KnuW4PAzk9DOt7XJYFetLzcHg";
        try {


            Claims claims = Jwts.parser()
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims);
        } catch (Exception e) {
            log.debug("validate is token error ", e);
        }
    }
    
}
