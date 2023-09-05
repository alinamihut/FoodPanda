package com.example.demo;

import com.assignment2.demo.dto.MenuItemDTO;
import com.assignment2.demo.model.*;
import com.assignment2.demo.repository.CategoryRepository;
import com.assignment2.demo.repository.MenuItemRepository;
import com.assignment2.demo.repository.RestaurantRepository;
import com.assignment2.demo.service.MenuService;
import com.assignment2.demo.service.RestaurantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class MenuServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private MenuService menuService;
    private Restaurant r1;
    private DeliveryZone d1;
    private DeliveryZone d2;

    private RestaurantAdministrator admin1;
    private List <FoodCategory> foodCategories = new ArrayList<>();

    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;

    private List <MenuItem> menuItems = new ArrayList<>();
    @Before
    public void setup() {
        r1 = new Restaurant();
        r1.setIdRestaurant(1);
        r1.setName("Samsara");
        r1.setLocation("Cluj-Napoca");


        d1 = new DeliveryZone();
        d1.setIdDeliveryZone(1);
        d1.setName("Centru");

        d2 = new DeliveryZone();
        d2.setIdDeliveryZone(2);
        d2.setName("Gheorgheni");

        List<DeliveryZone> deliveryZones1 = new ArrayList<>();
        deliveryZones1.add(d1);
        deliveryZones1.add(d2);

        r1.setDeliveryZones(deliveryZones1);

        admin1= new RestaurantAdministrator();
        admin1.setIdAdministrator(1);
        admin1.setUsername("admin1");
        admin1.setPassword("$2a$10$dEhUckcIjdCEDrivVYgcGulya51S00MH3IiJyTci4nbRqocGIj38e");

        r1.setAdministrator(admin1);

        restaurantRepository.save(r1);

        FoodCategory foodCategory1 = new FoodCategory(1,"LUNCH");
        FoodCategory foodCategory2 = new FoodCategory(1,"DINNER");

        foodCategories.add(foodCategory1);
        foodCategories.add(foodCategory2);

        categoryRepository.save(foodCategory1);
        categoryRepository.save(foodCategory2);
        menuItem1 = new MenuItem("Pasta", "with tomato sauce", 30);
        menuItem1.setCategory(foodCategory1);
        menuItem1.setIdFood(1);
        menuItem1.setRestaurant(r1);

        menuItem2 = new MenuItem("Pizza", "vegan", 33);
        menuItem2.setCategory(foodCategory1);
        menuItem2.setIdFood(2);
        menuItem2.setRestaurant(r1);

        menuItem3 = new MenuItem("Salad", "chicken/vegan", 28);
        menuItem3.setCategory(foodCategory1);
        menuItem3.setIdFood(3);
        menuItem3.setRestaurant(r1);

        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        menuItems.add(menuItem3);

    }

    @Test
    public void getAllFoodCategoriesTest() {
        Mockito.when(categoryRepository.findAll()).thenReturn(foodCategories);
        List<FoodCategory> foundCategories = menuService.getAllFoodCategories();
        assertNotNull(foundCategories);
        assertEquals(2, foundCategories.size());
    }

    @Test
    public void getAllMenuItemsByRestaurantNameTest(){
        Mockito.when(menuItemRepository.findAllByRestaurant_Name(r1.getName())).thenReturn(menuItems);
        List<MenuItem> menuItems = menuService.getAllMenuItemsByRestaurantName(r1.getName());
        assertNotNull(menuItems);
        assertEquals(3, menuItems.size());
    }


}
