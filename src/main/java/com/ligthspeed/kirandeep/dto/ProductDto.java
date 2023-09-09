package com.ligthspeed.kirandeep.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {


    @NotBlank(message = "{validation.error.name.notempty}")
    private String name;

    @DecimalMin(value = "0.1",message = "{validation.error.price.nonzero}" )
    private double price;

    @NotBlank(message = "{validation.error.baseUnit.notempty}")
    private String baseUnit;

    @DecimalMin(value = "0.1",message = "{validation.error.quantity.nonzero}" )
    private double quantity;

    private boolean isActive;


}