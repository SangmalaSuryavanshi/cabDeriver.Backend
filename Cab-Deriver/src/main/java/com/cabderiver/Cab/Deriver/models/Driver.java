package com.cabderiver.Cab.Deriver.models;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String licenseID;
    private String vehicleType;
    private String firstName;
    private String lastName;
    @Column(length=10, unique = true, nullable = false)
    private long phoneNumber;
    @Column(unique = true)
    private String emailID;
    private String password;
    private Double rating;
    private int totalRideServed;

    @OneToMany(mappedBy = "driver")
    private List<Booking>bookings;

}
