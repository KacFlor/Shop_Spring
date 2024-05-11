package com.KacFlor.ShopSpring.controllersRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct{

    private String sku;

    private String name;

    private String description;

    private Double price;

    private Double stock;

}
