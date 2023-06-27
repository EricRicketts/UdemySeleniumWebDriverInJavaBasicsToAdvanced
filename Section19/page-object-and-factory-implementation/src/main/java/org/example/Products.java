package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Products {

    List<Integer> productNumbers;



    @FindBy(how = How.CLASS_NAME, using = "card-body")
    public List<WebElement> allProducts;

    public Products(WebDriver driver) {
        productNumbers = new ArrayList<>();
        PageFactory.initElements(driver, this);
    }
}
