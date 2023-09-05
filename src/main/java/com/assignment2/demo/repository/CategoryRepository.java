package com.assignment2.demo.repository;

import com.assignment2.demo.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<FoodCategory, Integer> {
    Optional<FoodCategory>  findByName(String name);
}
