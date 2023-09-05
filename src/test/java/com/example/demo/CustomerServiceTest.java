package com.example.demo;

import com.assignment2.demo.dto.CustomerDTO;
import com.assignment2.demo.model.Customer;
import com.assignment2.demo.repository.CustomerRepository;
import com.assignment2.demo.service.CustomerService;
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
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private final List<Customer> customers= new ArrayList<>();
    private Customer c1 = new Customer();
    private Customer c2 = new Customer();
    private Customer c3 = new Customer();

    @Before
    public void setup() {
        c1.setIdCustomer(1);
        c1.setFirstName("Oana");
        c1.setLastName("Moisa");
        c1.setUsername("oanam");

        String hashedPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
        c1.setPassword(hashedPassword);


        c2.setIdCustomer(2);
        c2.setFirstName("Ileana");
        c2.setLastName("Pop");
        c2.setUsername("ileanapop");

        String hashedPassword2 = BCrypt.hashpw("1234", BCrypt.gensalt());
        c2.setPassword(hashedPassword2);

        c3.setIdCustomer(2);
        c3.setFirstName("Alina");
        c3.setLastName("M");
        c3.setUsername("alinam");

        String hashedPassword3 = BCrypt.hashpw("1234", BCrypt.gensalt());
        c3.setPassword(hashedPassword3);

        customers.add(c1);
        customers.add(c2);
        customers.add(c3);


    }


    @Test
    public void getListOfCustomersTest() {

        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> foundCustomers= customerService.getListOfCustomers();
        assertNotNull(foundCustomers);
        assertEquals(3, foundCustomers.size());
    }


    @Test
    public void findByUsernameTest() {
        Mockito.when(customerRepository.findCustomerByUsername(c1.getUsername())).thenReturn(Optional.ofNullable(c1));
        Optional<Customer> found = customerService.findByUsername(c1.getUsername());

        assertNotNull(found);
        assertEquals(c1.getUsername(), found.get().getUsername());
    }

    @Test
    public void findByIdTest() {
        Mockito.when(customerRepository.findCustomerByIdCustomer(c1.getIdCustomer())).thenReturn(Optional.ofNullable(c1));
        Optional<Customer> found = customerService.findById(c1.getIdCustomer());


        assertNotNull(found);
        assertEquals(c1.getIdCustomer(), found.get().getIdCustomer());
    }



    @Test
    public void loginCustomerFoundAndCorrectPasswordTest() {
        Mockito.when(customerRepository.findCustomerByUsername(c1.getUsername())).thenReturn(Optional.ofNullable(c1));
        Optional<Customer> found = customerService.findByUsername(c1.getUsername());


        assertEquals(c1.getUsername(), found.get().getUsername());
         assertEquals(c1.getPassword(), found.get().getPassword());
        assertNotNull(found);

    }

    @Test
    public void checkIfUsernameExistsTest(){
        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> allCustomers = customerService.getListOfCustomers();
        boolean found = false;
        for (Customer customer: allCustomers){
            if (customer.getUsername().equals("oanam")){
                found = true;
                assertTrue(found);
                break;
            }
        }

    }

     @Test
    public void insertUserTest() {
        CustomerDTO dto = new CustomerDTO("New", "User", "password", "username");
        Customer customer = Customer.builder().firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                username(dto.getUsername()).
                password(dto.getPassword()).
                build();

        String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(hashedPassword);

        assertTrue(customerService.insertUser(dto));

    }


}
