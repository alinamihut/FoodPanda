package com.assignment2.demo.security;

import java.util.Date;

import com.assignment2.demo.model.Customer;
import com.assignment2.demo.model.RestaurantAdministrator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JWTUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;


    private String secretKey ="Secret Key";

    public String generateAccessTokenForCustomer(Customer customer) {
        return Jwts.builder()
                .setSubject(customer.getUsername())
                .claim("username:" , customer.getUsername())
                .claim("role:" , "customer")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }

    public String generateAccessTokenForAdmin(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("username:" , username)
                .claim("role:" , "admin")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }
}