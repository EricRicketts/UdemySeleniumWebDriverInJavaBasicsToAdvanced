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

    @FindBy(how = How.CSS, using = "a[href$='/dynamic_loading/1']")
    public WebElement elementHiddenOnPageLink;

    @FindBy(how = How.CSS, using = "div#start > button")
    public WebElement startButton;

    @FindBy(how = How.CSS, using = "div#finish > h4")
    public WebElement fluentWaitResult;

    @FindBy(how = How.CSS, using = "div#loading > img")
    public WebElement loadingGIF;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
