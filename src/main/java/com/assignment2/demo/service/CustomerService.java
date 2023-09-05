package com.assignment2.demo.service;

import com.assignment2.demo.dto.CustomerDTO;
import com.assignment2.demo.model.Customer;
import com.assignment2.demo.repository.CustomerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


/**
 * Customer service class.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    Logger logger = Logger.getLogger(CustomerService.class.getName());

    /**
     * Instantiates a new Customer service.
     *
     * @param customerRepository the customer repository
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Insert customer.
     *
     * @param dto the customer dto
     * @return boolean true if customer was inserted successfully
     */
    public Boolean insertUser(CustomerDTO dto) {
        Customer customer = Customer.builder().firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                username(dto.getUsername()).
                password(dto.getPassword()).
                build();

        if (checkIfUsernameExists(customer.getUsername())){
            logger.warning("Could not insert customer");
            return false;
        }
        String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(hashedPassword);
        logger.info("Inserting customer in the db");
        customerRepository.save(customer);
        return true;
    }

    /**
    * Gets list of customers in the DB.
    *
    * @return the list of customers
    **/
    public List<Customer> getListOfCustomers() {
        logger.info("Retrieving all customers from the DB");
        return customerRepository.findAll();
    }

    /**
     * Find customer by username.
     *
     * @param username the username
     * @return customer object or null if the customer with doesnt't exist in the DB
     */
    public Optional<Customer> findByUsername(String username) {
        logger.info("Found customer with username " + username);
        return customerRepository.findCustomerByUsername(username);
    }

    /**
     * Login customer.
     *
     * @param username the username
     * @param password the password
     * @return boolean - true if customer was logged in or false if password is not correct or user doesn't exist in the DB
     */
    public Boolean loginCustomer(String username, String password) {
        Optional<Customer> customerFromDB = customerRepository.findCustomerByUsername(username);
        if (!customerFromDB.isPresent()) {
            logger.warning("Customer with username " + username + "doesn't exist in the DB");
            return false;
        } else {
            System.out.println(customerFromDB.get().getPassword());
            System.out.println(password);
            logger.info("Customer with username " + username + "logged in successfully");
            if (BCrypt.checkpw(password, customerFromDB.get().getPassword()))
                return true;

        }
        logger.warning("Customer with username " + username + "couldn't be logged in");
        return false;
    }

    /**
     * Check if username exists.
     *
     * @param username the username
     * @return boolean - true if admin exists in the DB or false if it doesn't
     */

    public boolean checkIfUsernameExists( String username){
        List<Customer> allCustomers = getListOfCustomers();
        for (Customer customer: allCustomers){
            if (customer.getUsername().equals(username)){
                logger.info("Found customer with username " + username);
                return true;
            }
        }
        logger.warning("Couldn't find admin with username " + username);
        return false;

    }
    /**
     * Find customer by id.
     *
     * @param id the customer id
     * @return customer object or null if the admin with doesn't exist in the DB
     */
    public Optional<Customer> findById(Integer id) {
        logger.info("Retrieving customer with id " + id + "from the DB");
        return customerRepository.findCustomerByIdCustomer(id);
    }

    /**
     * Delete admin.
     *
     * @param customerID the admin id
     */
    public void deleteCustomer(Integer customerID)
    {
        logger.info("Deleting customer with id " + customerID + "from the DB");
        customerRepository.deleteById(customerID);
    }


}
