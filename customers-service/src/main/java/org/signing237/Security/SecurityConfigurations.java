package org.signing237.Security;

import lombok.RequiredArgsConstructor;
import org.signing237.Security.Filters.CustomOncePerRequestFilter;
import org.signing237.Security.Filters.CustomUsernamePasswordAuthenticationFilter;
import org.signing237.ServiceImp.MyServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfigurations {

    private final MyServiceImp myService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter=
                 new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authConfig));
//                customUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        return http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login","**/refresh_token").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(new CustomOncePerRequestFilter(),CustomUsernamePasswordAuthenticationFilter.class)
                .addFilter(customUsernamePasswordAuthenticationFilter)
                .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider  authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(myService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
