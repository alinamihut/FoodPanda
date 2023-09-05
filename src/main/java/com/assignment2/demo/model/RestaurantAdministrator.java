package com.assignment2.demo.model;


import javax.persistence.*;

@Entity
@Table(name="administrator")
public class RestaurantAdministrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdministrator;

    @Column (name = "username", unique = true, nullable = false)
    private String username;

    @Column ( nullable = false)
    private String password;

    @OneToOne (mappedBy = "administrator")
    private Restaurant restaurant;

    public RestaurantAdministrator(int idAdministrator, String username, String password, Restaurant restaurant) {
        this.idAdministrator = idAdministrator;
        this.username = username;
        this.password = password;
        this.restaurant = restaurant;
    }

    public RestaurantAdministrator() {

    }

    public RestaurantAdministrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(int idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
