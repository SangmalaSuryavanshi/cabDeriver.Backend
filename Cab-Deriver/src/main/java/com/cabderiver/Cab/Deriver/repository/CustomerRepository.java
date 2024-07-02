package com.cabderiver.Cab.Deriver.repository;

import com.cabderiver.Cab.Deriver.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Customer findByEmail(String email);
}
