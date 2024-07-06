package com.cabderiver.Cab.Deriver.service;

import com.cabderiver.Cab.Deriver.exceptions.InvalidOperationException;
import com.cabderiver.Cab.Deriver.exceptions.ResourcesDoesNotExistException;
import com.cabderiver.Cab.Deriver.exceptions.UserNotFound;
import com.cabderiver.Cab.Deriver.models.AppUser;
import com.cabderiver.Cab.Deriver.models.Booking;
import com.cabderiver.Cab.Deriver.models.Customer;
import com.cabderiver.Cab.Deriver.models.Driver;
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
    DriverService driverService;

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

    public String updateBooking(String operation,
                              String email,
                              Integer bookingId){
        // we need to identify is this a customer email or this is a driver email
        Customer customer = customerService.getCustomerByEmail(email);
        Driver driver = driverService.getDriverByEmail(email);
        String userType = "";
        Integer userId = -1;
        AppUser user = null;
        if(customer != null){
            userId = customer.getId();
            userType = "CUSTOMER";
            user = customer;
        }else if(driver != null){
            userId = driver.getId();
            userType = "DRIVER";
            user = driver;

        }else{
            throw new UserNotFound(String.format("User with id %d does not exist",userId));
        }

        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if(booking==null){
            throw new ResourcesDoesNotExistException(String.format("Booking with id %d does not exist in the system", bookingId));

        }

        if(operation.equals("ACCEPT")){
            if(userType.equals("CUSTOMER")){
                throw new InvalidOperationException(String.format("Customer can not accept rides"));
            }
            // driver to accept the ride

            booking.setDriver(driver);
            booking.setStatus("ACCEPTED");
            booking.setBillAmount(100);
            bookingRepository.save(booking);
            return String.format("Driver with id %d accepted the booking with id %d"
                    ,userId,bookingId);
        }else if(operation.equals("CANCEL")){
            if(userType.equals("CUSTOMER")){
                if(booking.getCustomer().getId()==userId){
                    booking.setStatus("CANCELED");
                    bookingRepository.save(booking);
                    return String.format("Customer with id %d cancelled with ride with booking id %d"
                            ,userId, bookingId);
                }else{
                    throw new InvalidOperationException(String.format("Customer with id %d is not allowed to cancel booking with id %d"
                            ,userId,bookingId));
                }
            }else if(userType.equals("DRIVER")){
                if(booking.getDriver().getId()==userId){
                    booking.setStatus("CANCELLED");
                    bookingRepository.save(booking);
                    return String.format(
                            "Driver with id %d cancelled booking with id %d"
                             ,userId, bookingId);

                }else{
                    throw new InvalidOperationException(String.format("Driver with id %d is not allowed to cancel booking with id %d"
                            ,userId,bookingId));
                }
            }
        }
        return "";

    }

}
