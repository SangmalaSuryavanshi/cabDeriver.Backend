package com.cabderiver.Cab.Deriver.exceptions;

public class ResourcesDoesNotExistException extends RuntimeException{
    public ResourcesDoesNotExistException(String mssg){
        super(mssg);
    }
}
