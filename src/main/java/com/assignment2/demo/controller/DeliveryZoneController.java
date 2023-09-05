package com.assignment2.demo.controller;

import com.assignment2.demo.model.Customer;
import com.assignment2.demo.model.DeliveryZone;
import com.assignment2.demo.model.RestaurantAdministrator;
import com.assignment2.demo.service.CustomerService;
import com.assignment2.demo.service.DeliveryZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/deliveryzone")
public class DeliveryZoneController {


    @Autowired
    private DeliveryZoneService deliveryZoneService;
    Logger logger = Logger.getLogger(DeliveryZoneController.class.getName());
    @GetMapping(path="/get")

    public List<String> getAllDeliveryZones(){
        logger.info("GET method for finding all delivery zones");
        List<DeliveryZone> allDeliveryZones = deliveryZoneService.getDeliveryZones();
        List<String> zones = new ArrayList();
        for (DeliveryZone z:allDeliveryZones){
            zones.add(z.getName());

        }
        return zones;
    }
}
