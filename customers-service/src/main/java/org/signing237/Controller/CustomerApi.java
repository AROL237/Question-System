package org.signing237.Controller;

import jakarta.validation.Valid;
import org.signing237.DTO.CustomerLogin;
import org.signing237.DTO.CustomerRequest;
import org.signing237.DTO.CustomerResponse;
import org.signing237.Model.Customers;
import org.signing237.Service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

    private final MyService myService;

    public CustomerApi(MyService myService) {
        this.myService = myService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse register(@RequestBody @Valid CustomerRequest request){
        Customers customer = myService.saveCustomer(request);
        return CustomerResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .build();
    }

    @GetMapping("/login")
    public void login(@RequestBody @Valid CustomerLogin login){
        /**
         * todo: implement login functionality
         * todo: authentication and authorization service using jwt tokens
         */
    }
}
