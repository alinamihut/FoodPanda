package com.assignment2.demo.repository;

import com.assignment2.demo.model.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, Integer> {
    Optional<DeliveryZone> findDeliveryZoneByName (String name);
}
