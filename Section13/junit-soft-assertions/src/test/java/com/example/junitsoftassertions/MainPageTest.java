package com.example.junitsoftassertions;

import org.assertj.core.api.SoftAssertions;
import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
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
        Duration duration = Duration.ofSeconds(explicitWaitTime);
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
    public void testUsingSoftAssertions() {
        int expectedBrokenLinks = 1;
        int expectedUnbrokenLinks = 19;
        List<WebElement> unbrokenLinks = new ArrayList<>();
        List<WebElement> brokenLinks = new ArrayList<>();

        // create an instance of soft assertions
        // These assertions will run all tests and not stop when there is a test failure
        SoftAssertions softAssertions = new SoftAssertions();

        // scroll down to the footer and assert it is the viewport
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", mainPage.footerElement);

        boolean footerIsInView = wait.until(
                ExpectedConditionUtils.isVisibleInViewport(mainPage.footerElement)
        );
        softAssertions.assertThat(footerIsInView).isTrue();

        // cycle through all footer anchors check to see if they are valid
        mainPage.footerAnchorElements.forEach(anchor -> {
            String url = anchor.getAttribute("href");
            try {
                // to save time do not navigate to the website just grab the response header
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                // from the header obtain the response code
                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 400) {
                    unbrokenLinks.add(anchor);
                } else {
                    brokenLinks.add(anchor);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // make the assertions and then assert all
        softAssertions.assertThat(brokenLinks.size()).isEqualTo(expectedBrokenLinks);
        softAssertions.assertThat(unbrokenLinks.size()).isEqualTo(expectedUnbrokenLinks);
        softAssertions.assertAll();
    }
}
