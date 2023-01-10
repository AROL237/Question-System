package org.signing237;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerService {
//            @Autowired
//

    public static void main(String[] args) {
        SpringApplication.run(CustomerService.class,args);
    }

    /*
    - code to initialise the role table in database.
     */

//    @Bean
//    CommandLineRunner commandLineRunner(){
//        return args -> {
//            rolesRepository.save(Roles.builder().name(Role.USER.toString()).build());
//            rolesRepository.save(Roles.builder().name(Role.ADMIN.toString()).build());
//            rolesRepository.save(Roles.builder().name(Role.SUPER_ADMIN.toString()).build());
//        };
//    }
}