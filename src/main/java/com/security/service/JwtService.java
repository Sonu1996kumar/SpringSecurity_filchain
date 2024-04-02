package com.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.entity.PropertyUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration.time}")
    private int expiryTime;
    private Algorithm algorithm;


    //private final static String USER_NAME = "username";
    @PostConstruct
    public void postConstruct() {
//        System.out.println("postConstruct");
//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expiryTime);
        //we apply the algorithmkey to the algorithm so that the algorithm now also protected
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(PropertyUser user) {//who is calling user service
       return JWT.create()
                .withClaim("USER_NAME",user.getUserName())//work as set attribute
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    //verify the token and return username if valid
//    public String verifyToken(String token) {
//        return JWT.require(algorithm).build().verify(token).getClaim("USER_NAME").asString();
//    }
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim("USER_NAME").asString();
    }
}
