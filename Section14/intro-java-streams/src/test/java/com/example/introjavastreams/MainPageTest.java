package com.example.introjavastreams;

import org.assertj.core.api.SoftAssertions;
import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private WebDriverWait wait;
  private SoftAssertions softAssertions;
  private String[] namesArray = new String[]{
      "Alpha", "Beta", "Charlie", "Delta", "Abby", "Echo", "Foxtrot",
      "Golf", "Alternate", "Amoral", "Hotel", "India", "Juliet"
  };
  private List<String> names = Arrays.asList(namesArray);

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    ChromeOptions options = new ChromeOptions();
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);
    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
    softAssertions = new SoftAssertions();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testStreamsPartOne() {
    List<String> duplicateNames = new ArrayList<>();
    int expectedNumberOfNamesStartingWithA = 4;
    int expectedNamesGreaterThanFourCharacters = 9;
    // scroll to the footer element
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.footerElement);

    // assert the footer element is in the viewport
    boolean footerIsInView = wait.until(
        ExpectedConditionUtils.isVisibleInViewport(mainPage.footerElement)
    );
    softAssertions.assertThat(footerIsInView).isTrue();

    // use streams to find all names starting with "A"
    Long numberOfNamesStartingWithA =
      names.stream().filter(name -> name.startsWith("A")).count();

    softAssertions.assertThat(numberOfNamesStartingWithA)
        .isEqualTo(expectedNumberOfNamesStartingWithA);

    // create a stream in place from an array and then filter
    // the number names starting with "A"
    numberOfNamesStartingWithA =
        Stream.of(namesArray).filter(name -> name.startsWith("A")).count();

    softAssertions.assertThat(numberOfNamesStartingWithA)
        .isEqualTo(expectedNumberOfNamesStartingWithA);

    // create a duplicate list using streams
    names.stream().forEach(name -> duplicateNames.add(name));

    softAssertions.assertThat(names).isEqualTo(duplicateNames);

    // number of names with greater than four characters
    Long namesGreaterThanFourCharacters =
        names.stream().filter(name -> name.length() > 4).count();

    softAssertions.assertThat(namesGreaterThanFourCharacters)
            .isEqualTo(expectedNamesGreaterThanFourCharacters);

    // first name greater than four characters
    Optional<String> firstNameGreaterThanFourCharacters =
        names.stream().filter(name -> name.length() > 4).findFirst();

    softAssertions.assertThat(firstNameGreaterThanFourCharacters.get())
            .isEqualTo("Alpha");
    softAssertions.assertAll();
  }

  @Test
  public void testStreamsWithMap() {
    List<String> expectedNamesGreaterThanFourCharactersUpperCase =
        new ArrayList<>(List.of(new String[]{
            "Alpha".toUpperCase(), "Charlie".toUpperCase(), "Delta".toUpperCase(),
            "Echo".toUpperCase(), "Foxtrot".toUpperCase(), "Alternate".toUpperCase(),
            "Amoral".toUpperCase(), "India".toUpperCase(), "Juliet".toUpperCase()
        }));

    // use stream to first filter names with more than four letters and then
    // map the filtered list to upper case
    Stream<String> namesGreaterThanFourCharactersUpperCaseStream =
        names.stream().filter(name -> name.length() > 4).map(name -> name.toUpperCase());
    List<String> namesGreaterThanFourCharactersUpperCase =
      namesGreaterThanFourCharactersUpperCaseStream.toList();

    softAssertions.assertThat(expectedNamesGreaterThanFourCharactersUpperCase)
        .isEqualTo(namesGreaterThanFourCharactersUpperCase);
  }
}