package com.example.demo;

import com.assignment2.demo.dto.RestaurantDTO;
import com.assignment2.demo.model.DeliveryZone;
import com.assignment2.demo.model.Restaurant;
import com.assignment2.demo.model.RestaurantAdministrator;
import com.assignment2.demo.repository.AdminRepository;
import com.assignment2.demo.repository.DeliveryZoneRepository;
import com.assignment2.demo.repository.RestaurantRepository;
import com.assignment2.demo.service.DeliveryZoneService;
import com.assignment2.demo.service.RestaurantService;
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
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RestaurantServiceTest {


    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private DeliveryZoneRepository deliveryZoneRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private final List<Restaurant> restaurants= new ArrayList<>();
    private DeliveryZone d1 = new DeliveryZone();
    private DeliveryZone d2 = new DeliveryZone();
    private DeliveryZone d3 = new DeliveryZone();
    private DeliveryZone d4 = new DeliveryZone();
    private Restaurant r1;
    private Restaurant r2;

    @BeforeEach
    void setUp() {
        this.restaurantService
                = new RestaurantService(this.restaurantRepository, adminRepository, deliveryZoneRepository);
    }

    @Before
    public void setup() {
            r1 = new Restaurant();
            r1.setIdRestaurant(1);
            r1.setName("Samsara");
            r1.setLocation("Cluj-Napoca");


        d1.setIdDeliveryZone(1);
        d1.setName("Centru");


        d2.setIdDeliveryZone(2);
        d2.setName("Gheorgheni");

        List<DeliveryZone> deliveryZones1 = new ArrayList<>();
        deliveryZones1.add(d1);
        deliveryZones1.add(d2);

        r1.setDeliveryZones(deliveryZones1);

        RestaurantAdministrator admin1 = new RestaurantAdministrator();
        admin1.setIdAdministrator(1);
        admin1.setUsername("admin1");
        admin1.setPassword("$2a$10$dEhUckcIjdCEDrivVYgcGulya51S00MH3IiJyTci4nbRqocGIj38e");

        r1.setAdministrator(admin1);


        r2 = new Restaurant();
        r2.setIdRestaurant(2);
        r2.setName("Napoli Centrale");
        r2.setLocation("Cluj-Napoca");


        //DeliveryZone d3 = new DeliveryZone();
        d3.setIdDeliveryZone(3);
        d3.setName("Zorilor");

        //DeliveryZone d4= new DeliveryZone();
        d4.setIdDeliveryZone(4);
        d4.setName("Marasti");

        List<DeliveryZone> deliveryZones2 = new ArrayList<>();
        deliveryZones2.add(d3);
        deliveryZones2.add(d4);

        r2.setDeliveryZones(deliveryZones2);

        RestaurantAdministrator admin2 = new RestaurantAdministrator();
        admin2.setIdAdministrator(2);
        admin2.setUsername("admin2");
        admin2.setPassword("$2a$10$dEhUckcIjdCEDrivVYgcGulya51S00MH3IiJyTci4nbRqocGIj38e");

        r1.setAdministrator(admin2);
        restaurants.add(r1);
        restaurants.add(r2);


        deliveryZoneRepository.save(d1);
        deliveryZoneRepository.save(d2);
        deliveryZoneRepository.save(d3);
        deliveryZoneRepository.save(d4);


        RestaurantAdministrator admin3 = new RestaurantAdministrator();
        admin3.setIdAdministrator(2);
        admin3.setUsername("admin3");
        admin3.setPassword("$2a$10$dEhUckcIjdCEDrivVYgcGulya51S00MH3IiJyTci4nbRqocGIj38e");
        adminRepository.save(admin3);
        Mockito.when(restaurantRepository.findAll()).thenReturn(restaurants);
        Mockito.when(restaurantRepository.findRestaurantByAdministrator_Username("admin1")).thenReturn(Optional.of(r1));


        Mockito.when(adminRepository.findByUsername("admin3")).thenReturn(Optional.of(admin3));
    }



    @Test
    public void findRestaurantByAdminTest (){
        Optional<Restaurant> found = restaurantService.findRestaurantByAdminandGet("admin1");

        assertNotNull(found);
        assertEquals(r1.getName(), found.get().getName());
        assertEquals(r1.getIdRestaurant(), found.get().getIdRestaurant());

    }

    @Test
    public void insertRestaurantTest() {
        List<String> zones = new ArrayList<>();
        zones.add("Centru");
        zones.add("Zorilor");

        Mockito.when(deliveryZoneRepository.findDeliveryZoneByName("Centru")).thenReturn(Optional.of(d1));
        Mockito.when(deliveryZoneRepository.findDeliveryZoneByName("Gheorgheni")).thenReturn(Optional.of(d2));
        Mockito.when(deliveryZoneRepository.findDeliveryZoneByName("Zorilor")).thenReturn(Optional.of(d3));

        RestaurantDTO dto = new RestaurantDTO(r1.getName(), r1.getLocation(), "admin3", zones);
        assertTrue(restaurantService.insertRestaurant(dto));

    }


    @Test
    public void getListOfRestaurantsTest() {
        List<Restaurant> foundRestaurants = restaurantService.getListOfRestaurants();
        assertNotNull(foundRestaurants);
        assertEquals(2, foundRestaurants.size());

    }


}
