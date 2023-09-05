package com.assignment2.demo.controller;

import com.assignment2.demo.dto.MenuItemDTO;
import com.assignment2.demo.dto.OrderDTO;
import com.assignment2.demo.dto.RestaurantDTO;

import com.assignment2.demo.model.*;
import com.assignment2.demo.service.MenuService;
import com.assignment2.demo.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;

@RestController
@RequestMapping(path="/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    Logger logger = Logger.getLogger(RestaurantController.class.getName());
    @Autowired
    public RestaurantController(RestaurantService restaurantService, MenuService menuService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
    }

    @PostMapping(path = "/create", consumes = {"application/json"})
    public ResponseEntity create(@RequestBody RestaurantDTO restaurantDTO) {
        logger.info("POST method for creating a restaurant entity");
        if (restaurantService.insertRestaurant(restaurantDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New restaurant added!");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This admin has already registered a restaurant!");
    }

    @GetMapping(path = "/viewrestaurants")
    public List<RestaurantDTO> findRestaurants() {
        logger.info("GET method for retrieving all restaurants");
        ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) restaurantService.getListOfRestaurants();
        ArrayList<RestaurantDTO> restaurantDTOS = new ArrayList<>();


        for (Restaurant r : restaurants) {
            ArrayList<String> deliveryZonesString = new ArrayList<>();
            for (DeliveryZone zone : r.getDeliveryZones()) {
                deliveryZonesString.add(zone.getName());
            }
            RestaurantDTO dto = new RestaurantDTO(r.getName(), r.getLocation(), r.getAdministrator().getUsername(), deliveryZonesString);
            restaurantDTOS.add(dto);

        }
        return restaurantDTOS;
    }

    @PostMapping(path = "/addmenuitem", consumes = {"application/json"})
    public ResponseEntity create(@RequestBody MenuItemDTO menuItemDTO) {
        logger.info("POST method for creating a menu item entity");
        System.out.println(menuItemDTO.getName());
        System.out.println(menuItemDTO.getPrice());
        System.out.println(menuItemDTO.getDescription());
        if (menuItemDTO.getPrice() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Menu item couldn't be added!");
        }
        if (menuService.insertMenuItem(menuItemDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New menu item added!");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Menu item couldn't be added!");
    }

    @GetMapping(path = "/categories")
    public List<String> getAllCategories() {
        logger.info("GET method for retrieving all food categories");
        List<FoodCategory> allFoodCategories = menuService.getAllFoodCategories();
        List<String> categories = new ArrayList();
        for (FoodCategory category : allFoodCategories) {
            categories.add(category.getName());

        }
        return categories;
    }

    @GetMapping(path = "/menuitems/{name}")
    public List<MenuItemDTO> getAllMenuItemsForRestaurant(@PathVariable String name) {
        logger.info("GET method for retrieving all menu items for restaurant"  + name);
        ArrayList<MenuItem> menuItems = (ArrayList<MenuItem>) menuService.getAllMenuItemsByRestaurantName(name);
        ArrayList<MenuItemDTO> menuItemDTOS = new ArrayList<>();


        for (MenuItem m : menuItems) {
            MenuItemDTO dto = new MenuItemDTO(m.getName(), m.getDescription(), m.getPrice(), m.getCategory().getName());
            menuItemDTOS.add(dto);

        }
        return menuItemDTOS;
    }


    @GetMapping(path = "/exportmenu/{name}")
    public ResponseEntity exportMenu(@PathVariable String name) {
        logger.info("GET method for exporring the menu of restaurant "  + name);
        ArrayList<MenuItem> menuItems = (ArrayList<MenuItem>) menuService.getAllMenuItemsByRestaurantName(name);
        ArrayList<MenuItemDTO> menuItemDTOS = new ArrayList<>();

        for (MenuItem m : menuItems) {
            MenuItemDTO dto = new MenuItemDTO(m.getName(), m.getDescription(), m.getPrice(), m.getCategory().getName());
            menuItemDTOS.add(dto);

        }

        menuService.exportMenuAsPDF(menuItemDTOS, name);
        return ResponseEntity.status(HttpStatus.CREATED).body("Menu exported successfully!");

    }

    @GetMapping(path = "/{adminname}")
    public RestaurantDTO getRestaurantByAdmin(@PathVariable String adminname) {
        logger.info("GET method for finding restaurant for admin "  + adminname);
        Optional<Restaurant> r = restaurantService.findRestaurantByAdminandGet(adminname);
        System.out.println(r.get().getName());
        if (r.isPresent()) {
            ArrayList<String> deliveryZonesString = new ArrayList<>();
            for (DeliveryZone zone : r.get().getDeliveryZones()) {
                deliveryZonesString.add(zone.getName());
            }
            RestaurantDTO dto = new RestaurantDTO(r.get().getName(), r.get().getLocation(), r.get().getAdministrator().getUsername(), deliveryZonesString);
            return dto;
        }

        return null;
    }

    @PostMapping(path = "/sendemail", consumes = {"application/json"})
    public ResponseEntity makeOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("POST method for making an order");
        Optional<RestaurantAdministrator> admin = restaurantService.findAdminByRestaurant(orderDTO.getRestaurant());
        String adminEmail = admin.get().getUsername();
        try {
            restaurantService.sendmail(adminEmail, orderDTO);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Email sent successfully!");

    }


}


