package com.assignment2.demo.controller;

import com.assignment2.demo.dto.AdminDTO;
import com.assignment2.demo.dto.CustomerDTO;
import com.assignment2.demo.model.RestaurantAdministrator;
import com.assignment2.demo.security.JWTUtil;

import com.assignment2.demo.security.JwtResponse;
import com.assignment2.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/admin")
public class AdminController {
    private final AdminService adminService;
    private final JWTUtil jtwUtil = new JWTUtil();

    Logger logger = Logger.getLogger(AdminController.class.getName());
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;

    }


    @GetMapping
    public List<RestaurantAdministrator> findAll() {
        logger.info("GET method for finding all existing admins");
        return adminService.getListOfAdmins();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantAdministrator> findById(@PathVariable("id") Integer id){
        Optional<RestaurantAdministrator> admin = adminService.findById(id);
        logger.info("GET method for finding an admin");
        if(admin.isPresent()) {
            return ResponseEntity.ok().body(admin.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity loginAdmin(@RequestBody CustomerDTO resource){
        Boolean loginAdminMade = adminService.loginAdmin(resource.getUsername(), resource.getPassword());

        logger.info("POST method for logging in an admin");
        if(loginAdminMade) {
            Optional<RestaurantAdministrator> admin = adminService.findByUsername(resource.getUsername());
            String jwt = jtwUtil.generateAccessTokenForAdmin(resource.getUsername());
            System.out.println(jwt);

            logger.info("Generated token" + jwt);
            return new ResponseEntity<>(new JwtResponse(jwt, admin.get().getIdAdministrator(),
                    admin.get().getUsername(),
                    "admin"), HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping( path = "/create", consumes = {"application/json"})
    public ResponseEntity create(@RequestBody AdminDTO resource) {
        RestaurantAdministrator newAdministrator = new RestaurantAdministrator( resource.getUsername(), resource.getPassword());
        logger.info("POST method for adding an admin in the DB");
        if( adminService.insertAdmin(newAdministrator)){

            return  ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully!");
        }
        else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer>  delete(@PathVariable("id") Integer id) {
        logger.warning("DELETE method for deleting admin from the DB");
        adminService.deleteAdmin(id);

        return ResponseEntity.ok(id);
    }
}
