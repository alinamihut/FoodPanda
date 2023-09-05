package com.assignment2.demo.dto;

public class MenuItemDTO {
    private String name;
    private String description;
    private Integer price;
    private String category;
    private String admin;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public MenuItemDTO(String name, String description, Integer price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
