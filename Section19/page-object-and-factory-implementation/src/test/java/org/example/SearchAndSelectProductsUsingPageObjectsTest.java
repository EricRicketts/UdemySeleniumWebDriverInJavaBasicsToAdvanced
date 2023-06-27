package org.example;


import com.google.common.collect.Range;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchAndSelectProductsUsingPageObjectsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void oneTimeSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        Duration explicitDuration;
        int implicitWaitTime = 10;
        int explicitWaitTime = 15;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

        explicitDuration = Duration.ofSeconds(explicitWaitTime);
        wait = new WebDriverWait(driver, explicitDuration);
        driver.get(Login.URL);
    }

    @AfterMethod
    public void teardown() { driver.quit(); }

    @Test
    public void testSearchAndSelectProductsUsingPageObjects() {
        AtomicInteger carCountCounter = new AtomicInteger(0);

        Login login = new Login(driver);
        login.emailInput.sendKeys(Login.USERNAME);
        login.passwordInput.sendKeys(Login.PASSWORD);
        login.loginButton.click();

        Products products = new Products(driver);
        int maxNumberOfProductsToBuy = products.allProducts.size();
        int minNumberOfProductsToBuy = 1;
        RandomNumber randomNumber = new RandomNumber(maxNumberOfProductsToBuy, minNumberOfProductsToBuy);
        int numberOfProductsToBuy = randomNumber.generateRandomNumber();
        List<WebElement> allProducts = wait.until(
                ExpectedConditions.visibilityOfAllElements(products.allProducts)
        );
        Assert.assertEquals(allProducts.size(), products.allProducts.size());

        while (products.productNumbers.size() < numberOfProductsToBuy) {
            int randomProductNumber = randomNumber.generateRandomNumber();
            if (!products.productNumbers.contains(randomProductNumber)) {
                products.productNumbers.add(randomProductNumber);
            }
        }

        Range<Integer> validProductNumberRange = Range.closed(minNumberOfProductsToBuy, maxNumberOfProductsToBuy);
        for (int index = 0; index <= numberOfProductsToBuy - 1; index++) {
            int productNumber = products.productNumbers.get(index);
            Assert.assertTrue(validProductNumberRange.contains(productNumber));
        }

        Cart cart = new Cart(driver);
        for (int index = 0; index < products.productNumbers.size(); index++) {
            WebElement product = allProducts.get(index);
            product.findElement(By.cssSelector("button:nth-of-type(2)")).click();
            carCountCounter.getAndIncrement();
            Boolean carCountUpdated = wait.until(
                ExpectedConditions.textToBePresentInElement(cart.cartQuantity, String.valueOf(carCountCounter))
            );
            Assert.assertTrue(carCountUpdated);
        }
    }
}
