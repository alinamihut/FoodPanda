package com.assignment2.demo.service;


import com.assignment2.demo.controller.AdminController;
import com.assignment2.demo.model.Customer;
import com.assignment2.demo.model.RestaurantAdministrator;
import com.assignment2.demo.repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


/**
 * Admin service class.
 */
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    Logger logger = Logger.getLogger(AdminService.class.getName());
    /**
     * Instantiates a new Admin service.
     *
     * @param adminRepository the admin repository
     */
    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Gets list of admins in the DB.
     *
     * @return the list of admins
     */
    public List<RestaurantAdministrator> getListOfAdmins() {
        logger.info("Retrieving all admins from the DB");
        return adminRepository.findAll();
        }

    /**
     * Create restaurant administrator.
     *
     * @param restaurantAdministrator - the administrator
     * @return the restaurant administrator saved in the DB
     */
    public  RestaurantAdministrator create(RestaurantAdministrator restaurantAdministrator) {
        logger.info("Inserting admin in the db");
        return adminRepository.save(restaurantAdministrator);
    }

    /**
     * Insert admin.
     *
     * @param admin the admin
     * @return boolean true if admin was inserted successfully
     */
    public Boolean insertAdmin(RestaurantAdministrator admin) {

        if (checkIfUsernameExists(admin.getUsername())){
            return false;
        }
        String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(hashedPassword);
        logger.info("Inserting admin in the db");
        adminRepository.save(admin);
        return true;
    }

    /**
     * Check if username exists.
     *
     * @param username the username
     * @return boolean - true if admin exists in the DB or false if it doesn't
     */
    public boolean checkIfUsernameExists( String username){
        List<RestaurantAdministrator> allAdmins = getListOfAdmins();
        for (RestaurantAdministrator admin: allAdmins){
            if (admin.getUsername().equals(username)){
                logger.info("Found admin with username " + username);
                return true;
            }
        }
        logger.warning("Couldn't find admin with username " + username);
        return false;

    }

    /**
     * Login admin.
     *
     * @param username the username
     * @param password the password
     * @return boolean - true if admin was logged in or false if password is not correct or user doesn't exist in the DB
     */
    public Boolean loginAdmin(String username, String password) {
        Optional<RestaurantAdministrator> adminFromDB = adminRepository.findByUsername(username);
        if (!adminFromDB.isPresent()) {
            logger.warning("Admin with username " + username + "doesn't exist in the DB");
            return false;
        } else {
            if (BCrypt.checkpw(password, adminFromDB.get().getPassword())) {
                logger.info("Admin with username " + username + "logged in successfully");
                return true;
            }
        }
        logger.warning("Admin with username " + username + "coudn't be logged in");
        return false;
    }

    /**
     * Find admin by username.
     *
     * @param username the username
     * @return admin object or null if the admin with doesnt't exist in the DB
     */
    public Optional<RestaurantAdministrator> findByUsername(String username) {
        logger.info("Retrieving admin " + username + "from the DB");
        return adminRepository.findByUsername(username);
    }

    /**
     * Find admin by id.
     *
     * @param id the admin id
     * @return admin object or null if the admin with doesn't exist in the DB
     */
    public Optional<RestaurantAdministrator> findById(Integer id) {
        logger.info("Retrieving admin with id " + id + "from the DB");
        return adminRepository.findByIdAdministrator(id);
    }

    /**
     * Delete admin.
     *
     * @param adminID the admin id
     */
    public void deleteAdmin(Integer adminID) {
        logger.info("Deleting admin with id " + adminID + "from the DB");
        adminRepository.deleteById(adminID);
    }
    }

