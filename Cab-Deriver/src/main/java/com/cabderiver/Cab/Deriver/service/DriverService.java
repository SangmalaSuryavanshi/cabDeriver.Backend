package com.cabderiver.Cab.Deriver.service;

import com.cabderiver.Cab.Deriver.models.Driver;
import com.cabderiver.Cab.Deriver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;
    public void registerDriver(Driver driver){
        driverRepository.save(driver);

    }

    public Driver getDriverByEmail(String emailID){
        return driverRepository.findByEmailID(emailID);
    }
}
