package com.KacFlor.ShopSpring.controllersRequests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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
