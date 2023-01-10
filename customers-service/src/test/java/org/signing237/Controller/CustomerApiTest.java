package org.signing237.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.signing237.DTO.CustomerRequest;
import org.signing237.Model.Customers;
import org.signing237.Model.Role;
import org.signing237.Model.Roles;
import org.signing237.Repository.CustomersRepository;
import org.signing237.ServiceImp.MyServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
class CustomerApiTest {


    @Autowired
    MyServiceImp myService;
    CustomerRequest request;

    @MockBean
    private CustomersRepository customersRepository;

    @BeforeEach
    void setUp() {
        request= CustomerRequest.builder()
                .email("arollito1@gmail.com")
                .gender('M')
                .lastName("arol")
                .firstName("signing Nzonkem")
                .password("arollito123")
                .build();
    }

    @Test
    void  saveCustomer ()  {
        Customers customers = Customers.builder()
                .id(21L)
                .role(Roles.builder().name(Role.USER.toString()).build())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .email(request.getEmail())
                .gender(request.getGender())
                .build();
        Mockito.when(customersRepository.save(customers)).thenReturn(customers);
       Customers customers1 = myService.saveCustomer(request);
                assertEquals(
                        new Customers(
                        21L,
                        "arollito1@gmail.com",
                        "arollito123",
                        "signing Nzonkem",
                        "arol",
                        'M',
                        new Roles(1,"USER")
                        ),
                        customers1
                );

    }
}