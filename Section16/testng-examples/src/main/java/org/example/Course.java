package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Course {
    private String title;
    private String instructor;
    private BigDecimal hours;
    private BigDecimal cost;

    public Course(
            String title, String instructor, BigDecimal hours, BigDecimal cost
    ) {
        this.title = title;
        this.instructor = instructor;
        this.hours = hours;
        this.cost = cost;
    }
}
