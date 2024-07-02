package com.cabderiver.Cab.Deriver.repository;

import com.cabderiver.Cab.Deriver.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {
}
