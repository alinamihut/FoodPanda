package com.assignment2.demo.service;

import com.assignment2.demo.dto.OrderDTO;
import com.assignment2.demo.dto.RestaurantDTO;
import com.assignment2.demo.model.DeliveryZone;
import com.assignment2.demo.model.Restaurant;
import com.assignment2.demo.model.RestaurantAdministrator;
import com.assignment2.demo.repository.AdminRepository;
import com.assignment2.demo.repository.DeliveryZoneRepository;
import com.assignment2.demo.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Restaurant service class.
 */
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AdminRepository adminRepository;
    private final DeliveryZoneRepository deliveryZoneRepository;
    Logger logger = Logger.getLogger(RestaurantService.class.getName());
    /**
     * Instantiates a new Restaurant service.
     *
     * @param restaurantRepository   the restaurant repository
     * @param adminRepository        the admin repository
     * @param deliveryZoneRepository the delivery zone repository
     */
    public RestaurantService(RestaurantRepository restaurantRepository, AdminRepository adminRepository, DeliveryZoneRepository deliveryZoneRepository) {
        this.restaurantRepository = restaurantRepository;
        this.adminRepository = adminRepository;
        this.deliveryZoneRepository = deliveryZoneRepository;
    }

    /**
     * Find if restaurant for the specified admin exists
     *
     * @param usernameAdmin the username admin
     * @return boolean
     */
    public boolean findRestaurantByAdmin (String usernameAdmin){
        Optional <Restaurant> restaurant =  restaurantRepository.findRestaurantByAdministrator_Username(usernameAdmin);
        logger.info("Finding restaurant for admin" + usernameAdmin);
        return restaurant.isPresent();
    }

    /**
     * Find restaurant for the specified admin exists and return it.
     *
     * @param usernameAdmin the username admin
     * @return the restaurant object or null
     */
    public Optional <Restaurant> findRestaurantByAdminandGet (String usernameAdmin){
        logger.info("Finding restaurant for admin" + usernameAdmin);
        return restaurantRepository.findRestaurantByAdministrator_Username(usernameAdmin);
    }

    /**
     * Find admin for restaurant.
     *
     * @param name the restaurant name
     * @return the admin object or null
     */
    public Optional <RestaurantAdministrator> findAdminByRestaurant (String name){
        logger.info("Finding admin for restaurant" + name);
        return adminRepository.findRestaurantAdministratorByRestaurantName(name);
    }

    /**
     * Insert restaurant.
     *
     * @param restaurantDTO the restaurant dto
     * @return boolean - true of restaurant was inserted successfully
     */
    public Boolean insertRestaurant(RestaurantDTO restaurantDTO) {

        if (findRestaurantByAdmin(restaurantDTO.getAdmin())){
            logger.warning("Restaurant already exists in the DB");
            return false;
        }

        Restaurant newRestaurant = new Restaurant(restaurantDTO.getName(),restaurantDTO.getLocation());

        Optional<RestaurantAdministrator> admin = adminRepository.findByUsername(restaurantDTO.getAdmin());

        List<DeliveryZone> deliveryZones = new ArrayList();

        for (String zone:restaurantDTO.getDeliveryZones()){
            deliveryZones.add(deliveryZoneRepository.findDeliveryZoneByName(zone).get());
        }

        newRestaurant.setAdministrator(admin.get());
        newRestaurant.setDeliveryZones( deliveryZones);
        restaurantRepository.save(newRestaurant);
        logger.info("Restaurant inserted in the DB");
        return true;
    }

    /**
     * Gets list of restaurants.
     *
     * @return the list of restaurants in the DB
     */
    public List<Restaurant> getListOfRestaurants() {
        logger.info("Retrieving all restaurants from the DB");
        return restaurantRepository.findAll();
    }

    public void sendmail(String adminEmail, OrderDTO dto) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("********@gmail.com", "**********");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("tutorialspoint@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adminEmail));
        msg.setSubject("New order for restaurant " + dto.getRestaurant());

        msg.setSentDate(new Date());

        String s1 = "Customer Username: " + dto.getCustomer() + "<br/>";
        String s2 = "Ordered Items: " + "<br/>";

        StringBuilder stringBuilder = new StringBuilder();
        for (String item: dto.getMenuItems()){

            stringBuilder.append(item + "<br/>");
        }
        String messageString = s1 + s2 + stringBuilder.toString();


        MimeBodyPart messageBodyPart = new MimeBodyPart();
        msg.setContent(messageString,  "text/html");
        Transport.send(msg);

    }



}
