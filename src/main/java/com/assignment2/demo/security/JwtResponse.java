package com.assignment2.demo.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String role;


    public JwtResponse(String token, Integer id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return this.token;
    }
}
