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

@Getter
@Setter
@NoArgsConstructor
public class Products {

    List<Integer> productNumbers;

    private int maxNumberOfProductsToBuy;
    private int minNumberOfProductsToBuy;
    private int numberOfProductsToBuy;

    @FindBy(how = How.CLASS_NAME, using = "card-body")
    public List<WebElement> allProducts;

    public void fillProductNumbersList(RandomNumber randomNumber) {
        while (this.productNumbers.size() < this.getNumberOfProductsToBuy()) {
            int randomProductNumber = randomNumber.generateRandomNumber();
            if (!this.productNumbers.contains(randomProductNumber)) {
                this.productNumbers.add(randomProductNumber);
            }
        }
    }
    public Products(WebDriver driver, int minNumberOfProductsToBuy) {
        setMinNumberOfProductsToBuy(minNumberOfProductsToBuy);
        productNumbers = new ArrayList<>();
        PageFactory.initElements(driver, this);
    }
}
