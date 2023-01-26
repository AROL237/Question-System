package org.signing237.ServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.signing237.DTO.CustomerRequest;
import org.signing237.DTO.CustomerResponse;
import org.signing237.DTO.ResponseDto;
import org.signing237.Model.Customers;
import org.signing237.Model.Role;
import org.signing237.Model.Roles;
import org.signing237.Repository.CustomersRepository;
import org.signing237.Service.MyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyServiceImp implements MyService, UserDetailsService {

    private final CustomersRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customers customer = customerRepository.findByEmail(email);

        if (customer != null) {
            log.info("customer {}, found in database. ",customer.getFullName());
            return new User(customer.getUsername(), customer.getPassword(), customer.getAuthorities());
        }
        else {
            log.error("customer {} not found in database. ",email);
            throw new UsernameNotFoundException("customer not found in database");
        }
    }

    @Override
    public Customers saveCustomer(CustomerRequest request) {
        Customers customer = Customers.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .role(Roles.builder().id(1).name(Role.USER.toString()).build())
                .build();
        log.info("customer: {}",customer);
        return  customerRepository.save(customer);
    }

    @Override
    public ResponseDto getCustomers(int size, int page) throws Exception {
       try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Customers> resultList = customerRepository.findAll(pageable);

            List<Customers> customers = resultList.getContent();
            List<CustomerResponse> responseList = customers.stream().map(customer -> CustomerResponse.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .gender(customer.getGender())
                    .fullName(customer.getFullName())
                    .role(getRolesTOListOfString(customer.getRole()))
                    .accountNonExpired(customer.isAccountNonExpired())
                    .accountNonLocked(customer.isAccountNonLocked())
                    .enable(customer.isEnabled())
                    .build()
            ).collect(Collectors.toList());

            return ResponseDto.builder()
                    .customers(responseList)
                    .build();
        }catch (Exception ex){
           throw new Exception(ex.getMessage());
       }
    }

    private List<String> getRolesTOListOfString(Roles role) {
        return Arrays.asList(role.getName());
    }

}
