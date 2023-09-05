package com.assignment2.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRestaurant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;


    @OneToOne
    @JoinColumn(name = "idAdministrator")
    private RestaurantAdministrator administrator;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "delivery_zones",
            joinColumns = @JoinColumn(name = "idRestaurant"),
            inverseJoinColumns = @JoinColumn(name = "idDeliveryZone"))
    private List<DeliveryZone> deliveryZones;

    public Restaurant(String name, String location) {
        this.name = name;
        this.location = location;
    }


    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

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

    public RestaurantAdministrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(RestaurantAdministrator administrator) {
        this.administrator = administrator;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<DeliveryZone> getDeliveryZones() {
        return deliveryZones;
    }

    public void setDeliveryZones(List<DeliveryZone> deliveryZones) {
        this.deliveryZones = deliveryZones;
    }


}


