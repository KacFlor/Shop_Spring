package com.KacFlor.ShopSpring.controllersRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewShipment{

    private LocalDate shipmentDate;

    private String address;

    private String city;

    private String state;

    private String country;

    private String zipcode;

}
