package com.cabderiver.Cab.Deriver.repository;

import com.cabderiver.Cab.Deriver.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
