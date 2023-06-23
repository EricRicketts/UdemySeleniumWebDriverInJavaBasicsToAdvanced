package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SearchAndSelectProductsTest {

    final String userEmail = "elmer.fudd@warnerbros.com";
    final String userPassword = "Bugs123@bunny";
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeMethod
    public void setUp() {
        Duration duration;
        int implicitWaitTime = 10;
        int explicitWaitTime = 10;
        WebDriverManager.chromedriver().setup();
        String URL = "https://rahulshettyacademy.com/client";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

        duration = Duration.ofSeconds(explicitWaitTime);
        wait = new WebDriverWait(driver, duration);
        driver.get(URL);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testSelectAndBuyProducts() {
        int cartCount = 0; // used to track how many products are bought
        // this is the label element that holds the carCount
        String cartButtonCss = "button[routerlink=\"/dashboard/cart\"]";
        String cartLabelCss = "button[routerlink=\"/dashboard/cart\"] > label";

        driver.findElement(By.id("userEmail")).sendKeys(userEmail);
        driver.findElement(By.id("userPassword")).sendKeys(userPassword);
        driver.findElement(By.id("login")).click();

        WebElement headingForPageTitle = driver.findElement(By.cssSelector("div.mt-1 > h3"));
        Boolean pageHeadingMainTextFound = wait.until(
                ExpectedConditions.textToBePresentInElement(headingForPageTitle, "AUTOMATION")
        );

        Assert.assertTrue(pageHeadingMainTextFound);

        List<WebElement> listedProducts = driver.findElements(By.cssSelector("div.card"));

        for(WebElement product : listedProducts) {
            product.findElement(By.cssSelector("button.w-10")).click();
            WebElement cartQuantity = driver.findElement(By.cssSelector(cartLabelCss));
            cartCount += 1;
            Boolean cartCountUpdated = wait.until(
                    ExpectedConditions.textToBePresentInElement(cartQuantity, String.valueOf(cartCount))
            );
            Assert.assertTrue(cartCountUpdated);
        }


        WebElement cartButton = driver.findElement(By.cssSelector(cartButtonCss));
        cartButton.click();
        WebElement checkoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Checkout')]")
                )
        );
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", checkoutButton);
        javascriptExecutor.executeScript("arguments[0].click()", checkoutButton);

        WebElement paymentMethodElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),'Payment Method')]")
                )
        );

        Assert.assertNotNull(paymentMethodElement);
    }
}