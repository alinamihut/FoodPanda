package com.assignment2.demo.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="delivery_zone")
public class DeliveryZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeliveryZone;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy="deliveryZones")
    private List<Restaurant> restaurants;

    public DeliveryZone() {
    }

    public DeliveryZone(Integer idDeliveryZone, String name) {
        this.idDeliveryZone = idDeliveryZone;
        this.name = name;
    }

    public Integer getIdDeliveryZone() {
        return idDeliveryZone;
    }

    public void setIdDeliveryZone(Integer idDeliveryZone) {
        this.idDeliveryZone = idDeliveryZone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
