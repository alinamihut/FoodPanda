package com.assignment2.demo.dto;

import java.util.List;

public class RestaurantDTO {
    private String name;
    private String location;
    private String admin;
    private List<String> deliveryZones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<String> getDeliveryZones() {
        return deliveryZones;
    }

    public void setDeliveryZones(List<String> deliveryZones) {
        this.deliveryZones = deliveryZones;
    }

    public RestaurantDTO(String name, String location, String admin, List<String> deliveryZones) {
        this.name = name;
        this.location = location;
        this.admin = admin;
        this.deliveryZones = deliveryZones;
    }
}
