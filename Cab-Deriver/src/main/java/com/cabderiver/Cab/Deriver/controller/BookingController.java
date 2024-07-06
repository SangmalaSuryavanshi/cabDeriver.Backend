package com.cabderiver.Cab.Deriver.controller;

import com.cabderiver.Cab.Deriver.exceptions.InvalidOperationException;
import com.cabderiver.Cab.Deriver.exceptions.ResourcesDoesNotExistException;
import com.cabderiver.Cab.Deriver.exceptions.UserNotFound;
import com.cabderiver.Cab.Deriver.models.Booking;
import com.cabderiver.Cab.Deriver.requestbody.CustomerBookingRequestBody;
import com.cabderiver.Cab.Deriver.responsebody.BookingResponseBody;
import com.cabderiver.Cab.Deriver.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/update")
    public ResponseEntity updateBookingStatus(@RequestParam String opr,
                                              @RequestParam String email,
                                              @RequestParam Integer bookingId){
        try{
            String response = bookingService.updateBooking(opr,email, bookingId);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }catch (UserNotFound userNotFound){
            return new ResponseEntity(userNotFound.getMessage(),HttpStatus.NOT_FOUND);
        }catch (InvalidOperationException invalidOperationException){
            return new ResponseEntity(invalidOperationException.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
        }catch (ResourcesDoesNotExistException resourcesDoesNotExistException){
            return new ResponseEntity(resourcesDoesNotExistException.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
