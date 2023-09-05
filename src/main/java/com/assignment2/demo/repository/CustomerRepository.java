package com.assignment2.demo.repository;

import com.assignment2.demo.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends  JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByUsername (String username);

    Optional<Customer> findCustomerByIdCustomer (Integer id);

}
