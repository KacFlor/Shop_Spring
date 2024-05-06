package com.KacFlor.ShopSpring.controllersRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCustomer{

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Long phoneNumber;

}