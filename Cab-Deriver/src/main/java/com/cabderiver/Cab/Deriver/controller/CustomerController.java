package com.cabderiver.Cab.Deriver.controller;

import com.cabderiver.Cab.Deriver.exceptions.UserNotFound;
import com.cabderiver.Cab.Deriver.models.Customer;
import com.cabderiver.Cab.Deriver.requestbody.UserCredentialRequestBody;
import com.cabderiver.Cab.Deriver.service.CustomerService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/register")
    public String createAccount(@RequestBody Customer customer){
        customerService.registerAccount(customer);
        return "Account Created Successfully";

    }
    @GetMapping("/authenticate")
    public String loginCustomer(@RequestBody UserCredentialRequestBody userCredentialRequestBody){
        String email = userCredentialRequestBody.getEmail();
        String password = userCredentialRequestBody.getPassword();
        try {
            String AuthenticationDetails = customerService.authenticateCustomer(email, password);
            return AuthenticationDetails;

        }catch (UserNotFound userNotFound){
            return  userNotFound.getMessage();
        }



    }
}
