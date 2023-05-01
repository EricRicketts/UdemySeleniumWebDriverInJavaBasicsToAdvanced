package com.example.searchexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    @FindBy(how = How.CSS, using = "input#search-field")
    public WebElement searchField;

    @FindBy(how = How.XPATH, using = "//thead/tr/th[1]")
    public WebElement fruitOrVegetableHeading;

    @FindBy(how = How.XPATH, using = "//tbody/tr/td[1]")
    public List<WebElement> fruitOrVegetableNames;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
