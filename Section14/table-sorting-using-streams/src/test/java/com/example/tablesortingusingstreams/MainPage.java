package com.example.tablesortingusingstreams;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

// page_url = https://www.jetbrains.com/
public class MainPage {
    @FindBy(how = How.XPATH, using = "//h4[contains(text(), 'Brinjal')]")
    public WebElement vegetableH4;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Top')]")
    public WebElement topDealsLink;

    @FindBy(how = How.XPATH, using = "//thead/tr/th[1]")
    public WebElement tableHeadingVegetableOrFruitName;

    @FindBy(how = How.XPATH, using = "//tbody/tr/td[1]")
    public List<WebElement> fruitOrVegetableNames;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
