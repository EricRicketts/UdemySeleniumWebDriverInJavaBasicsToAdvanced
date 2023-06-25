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

    public int numberOfProductsToBuy(int totalNumberOfProducts) {
        int minimumNumberOfProductsToBuy = 1;
        Random randomNumber = new Random();
        return randomNumber.nextInt(
                totalNumberOfProducts - minimumNumberOfProductsToBuy + 1
        ) + minimumNumberOfProductsToBuy;
    }

    public int randomSelectionOfProduct(int totalNumberOfProducts) {
        int minimumProductNumberFromList = 0;
        int maximumProductNumberFromList = totalNumberOfProducts - 1;
        Random randomNumber = new Random();
        return randomNumber.nextInt(
                maximumProductNumberFromList - minimumProductNumberFromList + 1
        ) + maximumProductNumberFromList;
    }

    @FindBy(how = How.CLASS_NAME, using = "card-body")
    public List<WebElement> allProducts;

    @FindBy(how = How.TAG_NAME, using = "h5")
    public WebElement productHeading;

    @FindBy(how = How.CSS, using = "button:nth-of-type(2)")
    public WebElement addToCartButton;

    public Products(WebDriver driver) {
        productNumbers = new ArrayList<>();
        PageFactory.initElements(driver, this);
    }
}
