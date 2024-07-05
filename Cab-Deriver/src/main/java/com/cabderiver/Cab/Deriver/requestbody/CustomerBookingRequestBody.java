package com.cabderiver.Cab.Deriver.requestbody;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBookingRequestBody {
    private String startingLocation;
    private String endingLocation;

}
