package com.assignment2.demo.repository;

import com.assignment2.demo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository  extends JpaRepository<MenuItem, Integer> {
    Optional<MenuItem> findByName(String name);


    List<MenuItem> findAllByRestaurant_Name(String name);
}
