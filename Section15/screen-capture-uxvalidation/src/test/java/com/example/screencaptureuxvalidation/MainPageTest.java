package com.example.screencaptureuxvalidation;

import org.apache.commons.io.FileUtils;
import org.example.SetWebDriverLocation;
import org.openqa.selenium.OutputType;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private String url;
    private WebDriverWait wait;

    @BeforeClass
    public static void oneTimeSetup() {
        SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }

    @BeforeMethod
    public void setUp() {
        url = "https://www.rahulshettyacademy.com/angularpractice/";
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

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testScreenCapture() throws IOException {
        int nameInputWidth = 1110;
        int nameInputHeight = 38;
        int[] expectedDimensions = new int[]{nameInputWidth, nameInputHeight};

        // page load defined by footer visibility
        WebElement footer = wait.until(
                ExpectedConditions.visibilityOf(mainPage.footer)
        );
        Assert.assertNotNull(footer);

        // put something in the input field and then screen capture it
        mainPage.nameInput.sendKeys("Eric Ricketts");
        File screenshotNameInput = mainPage.nameInput.getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(screenshotNameInput,
                new File("screenshotNameInput.png"));

        // check the current working directory
        String currentWorkingDirectory = new File(".").getAbsolutePath();
        String expectedPath =
                "/Users/ericricketts/Documents/" +
                        "UdemySeleniumWebDriverInJavaBasicsToAdvanced/" +
                        "Section15/screen-capture-uxvalidation/.";
        Assert.assertEquals(currentWorkingDirectory, expectedPath);

        // get the path to the file and see if the file exists
        String pathToFile =
                "/Users/ericricketts/Documents/" +
                        "UdemySeleniumWebDriverInJavaBasicsToAdvanced/" +
                        "Section15/screen-capture-uxvalidation/screenshotNameInput.png";
        Path path = Paths.get(pathToFile);
        Assert.assertTrue(Files.exists(path));

        int actualNameInputWidth = mainPage.nameInput.getRect().getWidth();
        int actualNameInputHeight = mainPage.nameInput.getRect().getHeight();

        int[] actualDimensions = new int[]{actualNameInputWidth, actualNameInputHeight};

        Assert.assertEquals(actualDimensions, expectedDimensions);
    }
}
