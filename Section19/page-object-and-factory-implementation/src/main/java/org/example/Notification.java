package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Notification {

    private final String orderDetailsInCsvString = "Click To Download Order Details in CSV";
    private final String orderDetailsInExcelString = "Click To Download Order Details in Excel";

    @FindBy(how = How.CSS, using = "button.mb-3")
    public List<WebElement> notificationButtons;

    public Notification(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
