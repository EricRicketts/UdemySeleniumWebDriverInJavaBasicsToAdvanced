package com.example.getprices;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        int implicitWaitTime = 5;
        int explicitWaitTime = 10;
        Duration duration = Duration.ofSeconds(explicitWaitTime);
        String url = "https://rahulshettyacademy.com/seleniumPractise/#/offers";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
        driver.get(url);

        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, duration);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testGetPrices() {
        boolean fruitOrVegetableNameColumnHeadingIsVisible = wait.until(
                ExpectedConditions.textToBePresentInElement(
                        mainPage.fruitOrVegetableNameColumnHeading, "Veg/fruit name")
        );
        assertTrue(fruitOrVegetableNameColumnHeadingIsVisible);
    }
}