package com.cabderiver.Cab.Deriver.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String mssg){
        super(mssg);
    }

}
