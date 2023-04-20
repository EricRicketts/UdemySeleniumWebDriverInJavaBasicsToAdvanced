package com.example.brokenlinks;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private Duration duration;
    private WebDriverWait wait;

    @BeforeAll
    public static void oneTimeSetup() {
        SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }

    @BeforeEach
    public void setUp() {
        String url = "https://rahulshettyacademy.com/AutomationPractice/";
        int implicitWaitTime = 5;
        int explicitWaitTime = 10;
        duration = Duration.ofSeconds(explicitWaitTime);
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
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
    public void testBrokenLinks() throws InterruptedException {
        // scroll down to the footer
        int expectedNonEmptyAnchorLinks = 5;
        List<WebElement> nonEmptyFooterLinks = new ArrayList<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", mainPage.footerElement);

        // assert the footer is in view
        boolean footerIsInView = wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.footerElement));
        Assertions.assertTrue(footerIsInView);

        mainPage.footerLinks.forEach(anchor -> {
            String href = anchor.getAttribute("href");
            int hrefLength = href.length();
            if (!href.substring(hrefLength - 1).equals("#")) nonEmptyFooterLinks.add(anchor);
        });

        Assertions.assertEquals(expectedNonEmptyAnchorLinks, nonEmptyFooterLinks.size());
        Thread.sleep(2000);
    }
}
