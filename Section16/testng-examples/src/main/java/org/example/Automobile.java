package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Automobile {

    private String make = "";
    private String model = "";
    private int year;
    private BigDecimal price;

    public Automobile(
            String make, String model, int year, BigDecimal price
    ) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }
}
