package com.example.scrollingwithjavascrptexecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    @FindBy(how = How.CSS, using = "div.tableFixHead > table#product")
    public WebElement productTable;

    @FindBy(how = How.CSS, using = "div.tableFixHead > table#product > tbody > tr")
    List<WebElement> allProductRows;

    @FindBy(how = How.CSS, using = "div.tableFixHead > table#product > tbody > tr:nth-of-type(1)")
    WebElement firstProductRow;

    @FindBy(how = How.CSS, using = "div.tableFixHead > table#product > tbody > tr:nth-of-type(2)")
    WebElement secondProductRow;

    @FindBy(how = How.CSS, using = "div.tableFixHead > table#product > tbody > tr > td:nth-of-type(4)")
    List<WebElement> productAmounts;

    @FindBy(how = How.CSS, using = ".tableFixHead td:nth-child(4)")
    List<WebElement> alternativeProductAmounts;

    @FindBy(how = How.CSS, using = "div.totalAmount")
    WebElement totalAmountElement;

    @FindBy(how = How.CSS, using = "div.left-align table#product")
    WebElement coursePriceTable;

    @FindBy(how = How.CSS, using = "div.left-align table#product td:nth-child(3)")
    List<WebElement> coursePrices;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
