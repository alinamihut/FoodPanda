package com.assignment2.demo.dto;

import com.assignment2.demo.model.Restaurant;

public class AdminDTO {
    private String username;
    private String password;

    //private Restaurant restaurant;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    /*
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

     */
}
