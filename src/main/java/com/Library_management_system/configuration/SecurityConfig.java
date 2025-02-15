package com.Library_management_system.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig
{
    @Value("${student.authority}")
    private String studentAuthority;

    @Value("${admin.authority}")
    private String adminAuthority;

    //Bean for Authorization using for UserService
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/addStudent/**").permitAll()
                        .requestMatchers("/user/addAdmin/**").permitAll()
                        .requestMatchers("/user/filter/**").hasAnyAuthority(studentAuthority,adminAuthority)
                        .requestMatchers("/txn/issue/**").hasAuthority(adminAuthority)
                        .requestMatchers("/txn/return/**").hasAuthority(adminAuthority)
                        .requestMatchers("/book/addBook/**").hasAuthority(adminAuthority)
                        .requestMatchers("/book/filterBook/**").hasAnyAuthority(studentAuthority,adminAuthority)
                        .anyRequest().authenticated()
                ).formLogin(withDefaults())
                //.httpBasic(withDefaults());  // used for postman testing
                .httpBasic(withDefaults()).csrf(csrf-> csrf.disable()); //for post api at postman
        return http.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();

    }
}
