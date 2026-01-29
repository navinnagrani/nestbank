package com.nestbank.controller;

import com.nestbank.dto.LoginRequest;
import com.nestbank.entities.Customer;
import com.nestbank.repository.CustomerRepository;
import com.nestbank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthContoller {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        // 🔐 ADMIN LOGIN (Hardcoded for now)
        if ("admin".equals(username) && "admin123".equals(password)) {

            String token = JwtUtil.generateToken(
                    username,
                    "ADMIN",
                    null
            );

            return Map.of(
                    "token", token,
                    "role", "ADMIN"
            );
        }

        // CUSTOMER LOGIN (From Database)
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        // ⚠️ For now assuming password = "password"
        // Replace later with encrypted password check
        if (!"password".equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = JwtUtil.generateToken(
                customer.getEmail(),
                "CUSTOMER",
                customer.getId()
        );

        return Map.of(
                "token", token,
                "role", "CUSTOMER",
                "customerId", customer.getId()
        );
    }






    /*@Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String role;
        Customer customer = new Customer();
        if ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword())) {

        }
        else if ("user".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            role = "CUSTOMER";
            customer = customerRepository.findById()
        }
        else {
            throw new RuntimeException("Invalid credentials");
        }
        String token = JwtUtil.generateToken(request.getUsername(),role,customerId);
        return Map.of("token",token);
    }*/
}
