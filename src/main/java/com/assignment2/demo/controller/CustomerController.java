package com.assignment2.demo.controller;

import com.assignment2.demo.dto.CustomerDTO;
import com.assignment2.demo.model.Customer;
import com.assignment2.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    Logger logger = Logger.getLogger(CustomerController.class.getName());

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAll() {
        logger.info("GET method for finding all existing customers");
        return customerService.getListOfCustomers();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody CustomerDTO resource){
        System.out.println(resource.getUsername() + resource.getPassword());
        Boolean loginCustomerMade = customerService.loginCustomer(resource.getUsername(), resource.getPassword());

        logger.info("POST method for logging in a customer");
        if(loginCustomerMade) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping( path = "/create", consumes = {"application/json"})
    public ResponseEntity create(@RequestBody CustomerDTO resource) {
        logger.info("POST method for creating a customer");
        if( customerService.insertUser(resource)){
            return  ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
        }
        else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer>  delete(@PathVariable("id") Integer id) {
        logger.info("DELETE method for deleting a customer");
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(id);
    }

}
