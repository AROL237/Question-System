package org.signing237.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.signing237.DTO.CustomerRequest;
import org.signing237.DTO.CustomerResponse;
import org.signing237.DTO.ResponseDto;
import org.signing237.Model.Customers;
import org.signing237.Service.MyService;
import org.signing237.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private MyService myService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse registerCustomer(@RequestBody @Valid CustomerRequest request){
        Customers customer = myService.saveCustomer(request);
        return CustomerResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .build();
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getCustomers(
            @RequestParam int size,
            @RequestParam int page
    ) throws Exception {
        return ResponseEntity.ok().body( myService.getCustomers(size,page));
    }

    @RequestMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
            log.info("attempt refresh token");
            String authorizationHeader;
        authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader.startsWith(getBearerString()))
            {
                try {
                    String token = authorizationHeader.substring(getBearerString().length());
                    JwtUtil jwtUtil = new JwtUtil();
                    DecodedJWT decodedJWT =jwtUtil.decoderToken(token);
                    User user = new User(decodedJWT.getSubject(),"",jwtUtil.getAuthorities(decodedJWT.getClaim("roles")));

                    return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateToken(user));
                }catch (Exception e){
                    log.error(e.getMessage());
                    Map<String,String> error = new HashMap<>();
                    error.put("error",e.getMessage());
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(error);
                }
            }
            else
            {
                Map<String,String> error = new HashMap<>();
                error.put("error","'Authorization Header' not present in request, No refresh token to process response");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
    }

    private static String getBearerString() {
        return "Bearer ";
    }

}
