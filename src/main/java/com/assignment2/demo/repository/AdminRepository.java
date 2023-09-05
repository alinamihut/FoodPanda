package com.assignment2.demo.repository;

import com.assignment2.demo.model.RestaurantAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository <RestaurantAdministrator, Integer> {
    Optional<RestaurantAdministrator> findByUsername(String username);
    Optional<RestaurantAdministrator> findByIdAdministrator(Integer id);

    Optional<RestaurantAdministrator> findRestaurantAdministratorByRestaurantName(String name);
}
