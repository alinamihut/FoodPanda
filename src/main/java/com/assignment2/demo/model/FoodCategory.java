package com.assignment2.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="category")
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategory;

    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<MenuItem> food;

    public FoodCategory(Integer idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }

    public FoodCategory() {

    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getFood() {
        return food;
    }

    public void setFood(List<MenuItem> food) {
        this.food = food;
    }
}
