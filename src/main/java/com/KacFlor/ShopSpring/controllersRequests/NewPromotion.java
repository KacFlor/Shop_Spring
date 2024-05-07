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
public class NewPromotion{

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double discount;

}
