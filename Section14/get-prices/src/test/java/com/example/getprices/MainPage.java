package com.example.getprices;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
    @FindBy(how = How.XPATH, using = "//thead/tr/th[1]")
    public WebElement fruitOrVegetableNameColumnHeading;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
