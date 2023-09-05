package com.assignment2.demo.repository;


import com.assignment2.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findRestaurantByName(String name);

    Optional<Restaurant> findRestaurantByAdministrator_Username(String username);

}
