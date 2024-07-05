package com.cabderiver.Cab.Deriver.controller;

import com.cabderiver.Cab.Deriver.exceptions.UserNotFound;
import com.cabderiver.Cab.Deriver.models.Booking;
import com.cabderiver.Cab.Deriver.requestbody.CustomerBookingRequestBody;
import com.cabderiver.Cab.Deriver.responsebody.BookingResponseBody;
import com.cabderiver.Cab.Deriver.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")

public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/request")
    public String createCustomerBooking(@RequestBody CustomerBookingRequestBody customerBookingRequestBody,
                                      @RequestParam int customerId){
        String staringLocation = customerBookingRequestBody.getStartingLocation();
        String endingLocataion = customerBookingRequestBody.getEndingLocation();

        try{
            bookingService.handleCustomerRequest(staringLocation, endingLocataion, customerId);
            return "Waiting for driver to accept";
        }catch (UserNotFound userNotFound){
            return userNotFound.getMessage();

        }


    }
    @GetMapping("/all")
    public List<BookingResponseBody> getBookingByStatus(@RequestParam String state){
        return bookingService.getBookingByStatus(state);
    }
}
