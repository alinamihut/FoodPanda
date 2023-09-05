package com.assignment2.demo.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFood;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column ( nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "idCategory", nullable = false)
    private FoodCategory category;

    @ManyToOne
    @JoinColumn(name="idRestaurant", nullable=false)
    private Restaurant restaurant;

    @ManyToMany( mappedBy="orderMenuItems")
    private List<Order> orders;

    public MenuItem( String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public MenuItem() {

    }


    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
