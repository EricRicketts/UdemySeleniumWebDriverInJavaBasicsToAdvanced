package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SearchAndSelectProductsUsingStreamsTest {

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
        // I used AtomicInteger though I did not have to concern myself with Thread Safe operations
        // the class comes with a lot of useful utility methods
        AtomicInteger cartCount = new AtomicInteger(0);

        // this is the label element that holds the car count ie the number of products the user has selected
        String cartButtonCss = "button[routerlink=\"/dashboard/cart\"]";
        String cartLabelCss = "button[routerlink=\"/dashboard/cart\"] > label";

        // find and enter values for sign in and then click the login button
        driver.findElement(By.id("userEmail")).sendKeys(userEmail);
        driver.findElement(By.id("userPassword")).sendKeys(userPassword);
        driver.findElement(By.id("login")).click();

        // assert one has landed on the main page after login, in this case assert on what appears
        // to be the title of the main page
        WebElement headingForPageTitle = driver.findElement(By.cssSelector("div.mt-1 > h3"));
        Boolean pageHeadingMainTextFound = wait.until(
                ExpectedConditions.textToBePresentInElement(headingForPageTitle, "AUTOMATION")
        );

        Assert.assertTrue(pageHeadingMainTextFound);

        // grab the top level product element and use streams to map this list to the Add To Cart buttons
        // within a product element the Add to Cart button is the last of the two buttons
        List<WebElement> listedProducts = driver.findElements(By.cssSelector("div.card"));
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector("div.card")).stream()
                .map(product -> product.findElement(By.cssSelector("button:last-of-type"))).toList();

        // iterate through the list of Add To Cart buttons, click them and then assert the number of
        // items placed on order has been updated
        addToCartButtons.forEach((button) -> {
            button.click();
            // AtomicInteger update
            cartCount.getAndIncrement();
            WebElement cartQuantity = driver.findElement(By.cssSelector(cartLabelCss));
            Boolean cartCountUpdated = wait.until(
                    ExpectedConditions.textToBePresentInElement(cartQuantity, String.valueOf(cartCount))
            );
            Assert.assertTrue(cartCountUpdated);
        });

        // click the cart button to send the user to My Cart page
        WebElement cartButton = driver.findElement(By.cssSelector(cartButtonCss));
        cartButton.click();

        // use the JavascriptExecutor to manually scroll to the Checkout button as prior attempts
        // to click the button threw an exception.  This was because when attempting to click the
        // button it was not in view when the click took place
        WebElement checkoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Checkout')]")
                )
        );
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", checkoutButton);
        javascriptExecutor.executeScript("arguments[0].click()", checkoutButton);

        // assert one has landed on the checkout page
        WebElement paymentMethodElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),'Payment Method')]")
                )
        );

        Assert.assertNotNull(paymentMethodElement);
    }
}