package org.example.AbstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AbstractComponent {

    private final WebDriver driver;
    public List<WebElement> waitForElementsToAppear(By findBy) {
        int explicitWaitTime = 10;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
        List<WebElement> allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy));
        return !allProducts.isEmpty() ? allProducts : null;
    }

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
    }
}
