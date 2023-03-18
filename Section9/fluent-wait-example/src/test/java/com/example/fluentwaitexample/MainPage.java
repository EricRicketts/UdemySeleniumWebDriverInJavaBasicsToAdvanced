package com.example.fluentwaitexample;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    @FindBy(how = How.CSS, using = "li > a[href$='/dynamic_loading']")
    public WebElement dynamicLoadingLink;

    @FindBy(how = How.ID, using = "page-footer")
    public WebElement pageFooter;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
