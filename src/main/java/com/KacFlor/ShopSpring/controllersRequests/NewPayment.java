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
public class NewPayment{

    private LocalDate paymentDate;

    private String paymentMet;

    private Double amount;

}
