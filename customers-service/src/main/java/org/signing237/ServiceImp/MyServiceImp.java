package org.signing237.ServiceImp;

import org.signing237.DTO.CustomerRequest;
import org.signing237.Model.Customers;
import org.signing237.Model.Role;
import org.signing237.Model.Roles;
import org.signing237.Repository.CustomersRepository;
import org.signing237.Service.MyService;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImp implements MyService {
    private final CustomersRepository customerRepository;

    public MyServiceImp(CustomersRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customers saveCustomer(CustomerRequest request) {
        Customers customer = Customers.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .gender(request.getGender())
                .role(Roles.builder().id(1).name(Role.USER.toString()).build())
                .build();
        return  customerRepository.save(customer);

    }
}
