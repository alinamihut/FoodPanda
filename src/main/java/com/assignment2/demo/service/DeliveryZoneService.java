package com.assignment2.demo.service;

import com.assignment2.demo.model.DeliveryZone;
import com.assignment2.demo.repository.DeliveryZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 *  Delivery zone service.
 */
@Service
public class DeliveryZoneService {
    private final DeliveryZoneRepository deliveryZoneRepository;
    Logger logger = Logger.getLogger(DeliveryZoneService.class.getName());
    /**
     * Instantiates a new Delivery zone service.
     *
     * @param deliveryZoneRepository the delivery zone repository
     */
    public DeliveryZoneService(DeliveryZoneRepository deliveryZoneRepository) {
        this.deliveryZoneRepository = deliveryZoneRepository;
    }

    /**
     * Gets all delivery zones.
     *
     * @return the list of delivery zones in the DB
     */
    public List<DeliveryZone> getDeliveryZones() {
        logger.info("Retrieving all delivery zones from the DB");
        return deliveryZoneRepository.findAll();
    }
}
