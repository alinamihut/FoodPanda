package com.example.demo;

import com.assignment2.demo.dto.AdminDTO;
import com.assignment2.demo.dto.CustomerDTO;
import com.assignment2.demo.model.Customer;
import com.assignment2.demo.model.Restaurant;
import com.assignment2.demo.model.RestaurantAdministrator;
import com.assignment2.demo.repository.AdminRepository;
import com.assignment2.demo.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class  AdministratorServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    private final List<RestaurantAdministrator> admins= new ArrayList<>();
    private RestaurantAdministrator a1 = new RestaurantAdministrator();
    private RestaurantAdministrator a2 = new RestaurantAdministrator();
    private RestaurantAdministrator a3 = new RestaurantAdministrator();

    @Before
    public void setup() {

        a1.setIdAdministrator(1);
        a1.setUsername("admin1");

        String hashedPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
        a1.setPassword(hashedPassword);


        a2.setIdAdministrator(2);
        a2.setUsername("admin2");

        String hashedPassword2 = BCrypt.hashpw("1234", BCrypt.gensalt());
        a2.setPassword(hashedPassword2);

        a3.setIdAdministrator(3);
        a3.setUsername("admin3");

        String hashedPassword3 = BCrypt.hashpw("1234", BCrypt.gensalt());
        a3.setPassword(hashedPassword3);

        admins.add(a1);
        admins.add(a2);
        admins.add(a3);


    }

    @Test
    public void getListOfAdminsTest() {

        Mockito.when(adminRepository.findAll()).thenReturn(admins);
        List<RestaurantAdministrator> foundAdmins= adminService.getListOfAdmins();
        assertNotNull(foundAdmins);
        assertEquals(3, foundAdmins.size());
    }

    @Test
    public void insertAdmin() {

        RestaurantAdministrator admin = new RestaurantAdministrator( "admin", "1234");

        String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(hashedPassword);

        assertTrue(adminService.insertAdmin(admin));
    }


    @Test
    public void checkIfUsernameExists(){
        Mockito.when(adminRepository.findAll()).thenReturn(admins);
        List<RestaurantAdministrator> allAdmins = adminService.getListOfAdmins();
        boolean found = false;
        for (RestaurantAdministrator admin:allAdmins){
            if (admin.getUsername().equals("admin1")){
                found = true;
                assertTrue(found);
                break;
            }
        }
    }

    @Test
    public void loginAdminTest() {
        Mockito.when(adminRepository.findByUsername(a1.getUsername())).thenReturn(Optional.ofNullable(a1));
        Optional<RestaurantAdministrator> found = adminService.findByUsername(a1.getUsername());


        assertEquals(a1.getUsername(), found.get().getUsername());
        assertEquals(a1.getPassword(), found.get().getPassword());
        assertNotNull(found);
    }


    @Test
    public void findByUsernameTest() {
        Mockito.when(adminRepository.findByUsername(a1.getUsername())).thenReturn(Optional.ofNullable(a1));
        Optional<RestaurantAdministrator> found = adminService.findByUsername(a1.getUsername());

        assertNotNull(found);
        assertEquals(a1.getUsername(), found.get().getUsername());
    }
    @Test
    public void findByIdTest() {
        Mockito.when(adminRepository.findByIdAdministrator(a1.getIdAdministrator())).thenReturn(Optional.ofNullable(a1));
        Optional<RestaurantAdministrator> found = adminService.findById(a1.getIdAdministrator());

        assertNotNull(found);
        assertEquals(a1.getIdAdministrator(), found.get().getIdAdministrator());
    }
}
