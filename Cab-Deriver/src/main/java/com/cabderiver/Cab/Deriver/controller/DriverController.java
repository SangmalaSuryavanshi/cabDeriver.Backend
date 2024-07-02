package com.cabderiver.Cab.Deriver.controller;

import com.cabderiver.Cab.Deriver.models.Driver;
import com.cabderiver.Cab.Deriver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {
    @Autowired
    DriverService driverService;
    @PostMapping("/api/driver/register")

    public String createAccount(@RequestBody Driver driver){
        // Calling driver service
        driverService.registerDriver(driver);
        return "Driver got successfully register";

    }
}
