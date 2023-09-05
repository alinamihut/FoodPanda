package com.example.demo;

import com.assignment2.demo.controller.AdminController;
import com.assignment2.demo.model.DeliveryZone;
import com.assignment2.demo.repository.DeliveryZoneRepository;
import com.assignment2.demo.service.DeliveryZoneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



@RunWith(MockitoJUnitRunner.class)
public class DeliveryZoneServiceTest {

    private final List<DeliveryZone> deliveryZones= new ArrayList<>();
    @Mock
    private DeliveryZoneRepository deliveryZoneRepository;

    @InjectMocks
    private DeliveryZoneService deliveryZoneService;

    @BeforeEach
    void setUp() {
        this.deliveryZoneService
                = new DeliveryZoneService(this.deliveryZoneRepository);
    }


    @Before
    public void setup() {
        DeliveryZone d1 = new DeliveryZone();
        d1.setIdDeliveryZone(1);
        d1.setName("Zorilor");

        DeliveryZone d2 = new DeliveryZone();
        d2.setIdDeliveryZone(2);
        d1.setName("Gheorgheni");

        deliveryZones.add(d1);
        deliveryZones.add(d2);

        Mockito.when(deliveryZoneRepository.findAll()).thenReturn(deliveryZones);
    }


    @Test
    public void getDeliveryZones() {
         List<DeliveryZone> foundDeliveryZones = deliveryZoneService.getDeliveryZones();
        assertNotNull(foundDeliveryZones);
        assertEquals(2, foundDeliveryZones.size());
    }
}
