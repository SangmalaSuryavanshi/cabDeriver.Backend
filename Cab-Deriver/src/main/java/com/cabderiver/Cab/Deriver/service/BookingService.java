package com.cabderiver.Cab.Deriver.service;

import com.cabderiver.Cab.Deriver.exceptions.UserNotFound;
import com.cabderiver.Cab.Deriver.models.Booking;
import com.cabderiver.Cab.Deriver.models.Customer;
import com.cabderiver.Cab.Deriver.repository.BookingRepository;
import com.cabderiver.Cab.Deriver.responsebody.BookingResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    CustomerService customerService;


    @Autowired
    BookingRepository bookingRepository;

    public void handleCustomerRequest(String startingLocation,
                                      String endingLocation,
                                      int customerId) {
        // We need to validate is it a valid customer or not
        // If the customerId exist in our system so wll say customer is valid
        // else we well said customer is not valid
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new UserNotFound(String.format("User with id %d does not exits", customerId));
        }
        //booking created but not excepted
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setStatus("Draft");
        booking.setBillAmount(0);
        booking.setStartingLocation(startingLocation);
        booking.setEndingLocation(endingLocation);

        bookingRepository.save(booking);
        // We need to create a booking
    }

    public List<BookingResponseBody> getBookingByStatus(String state) {
        List<Booking> bookingList = bookingRepository.getBookingByStatus(state);
        List<BookingResponseBody> bookingResponseBodyList = new ArrayList<>();
        for(Booking booking : bookingList) {
            BookingResponseBody bookingResponseBody = new BookingResponseBody();
            bookingResponseBody.setBookingID(booking.getId());
            bookingResponseBody.setCustomerID(booking.getCustomer().getId());
            bookingResponseBody.setStartingLocation(booking.getStartingLocation());
            bookingResponseBody.setEndingLocation(booking.getEndingLocation());
            bookingResponseBody.setCustomerName(booking.getCustomer().getFirstName());
            bookingResponseBody.setStatus(booking.getStatus());
            bookingResponseBodyList.add(bookingResponseBody);


        }

        return bookingResponseBodyList;
    }

}
