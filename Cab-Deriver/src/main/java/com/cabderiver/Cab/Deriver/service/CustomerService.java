package com.cabderiver.Cab.Deriver.service;

import com.cabderiver.Cab.Deriver.exceptions.UserNotFound;
import com.cabderiver.Cab.Deriver.models.Customer;
import com.cabderiver.Cab.Deriver.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer getCustomerById(int customerID){
        return customerRepository.findById(customerID).orElse(null);
    }

    public void registerAccount(Customer customer){
        customerRepository.save(customer);

    }
    public String authenticateCustomer(String email, String password){
        Customer customer = customerRepository.findByEmail(email);

        if(customer==null){
            throw new UserNotFound(String.format("User with email %s does not exist",email));
        }

        String originalPassword = customer.getPassword();
        if(originalPassword.equals(password)){
            return "Authentication Successfull";
        }
        else
            return "Authentication failled";

    }


    public Customer getCustomerByEmail(String emailID){
       return customerRepository.findByEmail(emailID);
    }
}
