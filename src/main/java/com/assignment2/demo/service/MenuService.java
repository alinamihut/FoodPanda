package com.assignment2.demo.service;

import com.assignment2.demo.dto.MenuItemDTO;
import com.assignment2.demo.model.*;
import com.assignment2.demo.repository.CategoryRepository;
import com.assignment2.demo.repository.MenuItemRepository;
import com.assignment2.demo.repository.RestaurantRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Menu service.
 */
@Service
public class MenuService {

    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    Logger logger = Logger.getLogger(MenuService.class.getName());
    /**
     * Instantiates a new Menu service.
     *
     * @param categoryRepository   the category repository
     * @param menuItemRepository   the menu item repository
     * @param restaurantRepository the restaurant repository
     */
    public MenuService(CategoryRepository categoryRepository, MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.categoryRepository = categoryRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }


    /**
     * Insert menu item.
     *
     * @param menuITemDTO the menu item dto
     * @return boolean - true if menu item was inserted successfully and false otherwise
     */
    public Boolean insertMenuItem(MenuItemDTO menuITemDTO) {


        Optional<Restaurant> restaurant = restaurantRepository.findRestaurantByAdministrator_Username(menuITemDTO.getAdmin());
        Optional <FoodCategory> category = categoryRepository.findByName(menuITemDTO.getCategory());
        if (menuITemDTO.getPrice() <=0){
            logger.warning("Menu item not valid");
            return false;

        }
        MenuItem newMenuItem = new MenuItem(menuITemDTO.getName(), menuITemDTO.getDescription(), menuITemDTO.getPrice());
        if (category.isPresent()) {

            newMenuItem.setCategory(category.get());
        }
        else {
            logger.warning("Menu item not valid");
            return false;
        }
        if (restaurant.isPresent()){
            newMenuItem.setRestaurant(restaurant.get());
        }
        else {
            logger.warning("Menu item not valid");
            return false;
        }

        System.out.println(newMenuItem.getName());
        System.out.println(newMenuItem.getPrice());
        System.out.println(newMenuItem.getDescription());
        System.out.println(newMenuItem.getCategory().getName());
        System.out.println(newMenuItem.getRestaurant().getName());
        menuItemRepository.save(newMenuItem);
        logger.warning("Menu item inserted in the DB");
        return true;
    }

    /**
     * Gets all food categories in the DB.
     *
     * @return the list of all food categories in the DB
     */
    public List<FoodCategory> getAllFoodCategories() {
        logger.info("Retrieving all mfood categories from the DB" );
        return categoryRepository.findAll();
    }

    /**
     * Get all menu items by restaurant name.
     *
     * @param name the name
     * @return the list of menu items for a restaurant
     */
    public List<MenuItem> getAllMenuItemsByRestaurantName(String name){
        logger.info("Retrieving all menu items for restaurant" + name);
        return menuItemRepository.findAllByRestaurant_Name(name);
    }


    /**
     * Export menu as pdf.
     *
     * @param menuItemDTOS the menu item dtos
     * @param name         the name
     */
    public void exportMenuAsPDF (ArrayList<MenuItemDTO> menuItemDTOS, String name){
        Document document = new Document();
        try
        {
            logger.info("Exporting menu as pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Menu_Restaurant_" + name + ".pdf"));
            document.open();
            Font redFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, new CMYKColor(0, 255, 255, 0));
            Paragraph title = new Paragraph("Menu for Restaurant " + name, redFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
            for (MenuItemDTO dto:menuItemDTOS) {
                Font blackFontBold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, new CMYKColor(0, 0, 0, 255));
                Font blackFontBold2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new CMYKColor(0, 0, 0, 255));
                Font blackFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new CMYKColor(0, 0, 0, 255));
                String s = dto.getName();
                String price = "Price: " + dto.getPrice();
                String s2 = "Category: " + dto.getCategory();
                String s3 = "Details: " + dto.getDescription();
                Paragraph menuItemName = new Paragraph(s, blackFontBold);
                document.add(menuItemName);
                Paragraph menuItemPrice = new Paragraph(price, blackFontBold2);
                menuItemPrice.setAlignment(Element.ALIGN_RIGHT);
                document.add(menuItemPrice);
                Paragraph menuItemCategory = new Paragraph(s2, blackFont);
                document.add(menuItemCategory);
                Paragraph menuItemDetails = new Paragraph(s3, blackFont);
                document.add(menuItemDetails);
                Paragraph p = new Paragraph("----------------------------------------------------------------------", blackFont);
                document.add(p);
            }
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
