package org.example;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Book {

    private String title;
    private String author;
    private String publisher;
    private int year;
    private BigDecimal price;

    public Book(
            String title, String author, String publisher, int year, BigDecimal price
    ) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.price = price;
    }
}
