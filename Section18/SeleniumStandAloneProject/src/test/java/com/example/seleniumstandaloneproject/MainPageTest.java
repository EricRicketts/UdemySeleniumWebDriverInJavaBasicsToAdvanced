package com.example.seleniumstandaloneproject;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
    private static final String chromeDriverProperty = "webdriver.chrome.driver";
    private static final String webDriversFolderPC = "C:\\Program Files\\WebDrivers\\";
    private static final String chromeDriverWindows = "chromedriver.exe";
    private WebDriver driver;

    private WebDriverWait wait;

    @BeforeClass
    public void oneTimeSetup() {
        System.setProperty(chromeDriverProperty, webDriversFolderPC + chromeDriverWindows);
    }

    @BeforeMethod
    public void setUp() {
        int waitTime = 10;
        String url = "https://rahulshettyacademy.com/client";
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void findAndBuyProduct() {
        // login to the e-commerce website
        int numberOfProducts = 3;
        driver.findElement(By.id("userEmail")).sendKeys("elmer.fudd@warnerbros.com");
        driver.findElement(By.id("userPassword")).sendKeys("Bugs123@bunny");
        driver.findElement(By.id("login")).click();

        // ensure we have landed on the e-commerce page
        Boolean landOnECommercePage = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(By.id("sidebar"),"Filters")
        );
        Assert.assertTrue(landOnECommercePage);

        List<WebElement> allProducts = driver.findElements(By.className("mb-3"));
        Assert.assertEquals(allProducts.size(), numberOfProducts);

    }
}
