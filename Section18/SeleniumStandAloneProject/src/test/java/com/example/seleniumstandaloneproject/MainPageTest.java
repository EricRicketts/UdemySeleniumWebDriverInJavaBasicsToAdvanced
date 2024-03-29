package com.example.seleniumstandaloneproject;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

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
        WebElement firstCoatFind = null;
        String coatText = "zara coat 3";
        String email = "elmer.fudd@warnerbros.com";
        int numberOfProducts = 3;
        driver.findElement(By.id("userEmail")).sendKeys("elmer.fudd@warnerbros.com");
        driver.findElement(By.id("userPassword")).sendKeys("Bugs123@bunny");
        driver.findElement(By.id("login")).click();

        // ensure we have landed on the e-commerce page
        Boolean landOnECommercePage = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(By.id("sidebar"), "Filters")
        );
        Assert.assertTrue(landOnECommercePage);

        // find the coat which will be purchased do it by a for loop
        List<WebElement> products = driver.findElements(By.className("mb-3"));
        Assert.assertEquals(products.size(), numberOfProducts);
        for (WebElement product : products) {
            WebElement desiredProduct = product.findElement(By.className("card-body"));
            String desiredProductText = desiredProduct.findElement(By.tagName("h5")).getText();
            if (desiredProductText.equalsIgnoreCase(coatText)) {
                firstCoatFind = desiredProduct;
                break;
            }
        }
        Assert.assertNotNull(firstCoatFind);

        // find the coat which will be purchased do it by using streams
        WebElement secondCoatFind = products.stream().filter(product ->
                product.findElement(By.tagName("b")).getText().equalsIgnoreCase(coatText))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(secondCoatFind);

        // add the coat to the cart
        WebElement addToCartButton = secondCoatFind.findElement(By.cssSelector("button:last-child"));
        Assert.assertEquals(addToCartButton.getText().toLowerCase(), "add to cart");
        addToCartButton.click();

        // the first part of validating an item has been added to the cart is if the toast-container
        // appears, this is the alert that says the item has been added to cart
        // it is also necessary to check that the overlay disappear, rahul shetty has provided
        // the class name, "ng-animating".  Assert that the overlay disappears first and then the alter
        // appears this seems to be the normal sequence
        WebElement addToCartAlert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("toast-container"))
        );

        Boolean overlayDisappears = wait.until(
                ExpectedConditions.invisibilityOf(driver.findElement(By.className("ng-animating")))
        );
        Assert.assertTrue(overlayDisappears);

        Assert.assertNotNull(addToCartAlert);

        // check that the cart is updated, should have 1 item in the cart
        List<WebElement> dashboardButtons = driver.findElements(By.className("btn-custom"));
        WebElement cartButton = dashboardButtons.stream().filter(button ->
                button.getText().contains("Cart"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(cartButton);
        Boolean cartUpdated = wait.until(
                ExpectedConditions.textToBePresentInElement(cartButton.findElement(By.tagName("label")), "1")
        );
        Assert.assertTrue(cartUpdated);

        // now that the wait for the overlay to clear and the alter has been checked as preset, and we have
        // asserted the cart button is updated, it is now time to go to the cart and run assertions on the cart page
        cartButton.click();
        WebElement myCartHeading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(myCartHeading.getText().equalsIgnoreCase("my cart"));

        // check that there is only one item in the cart and that it is the coat
        // class for cart is .cartWrap

        WebElement cart = driver.findElement(By.className("cart"));
        WebElement cartList = driver.findElements(By.className("cartWrap")).get(0);
        String cartTagName = cartList.getTagName();
        Assert.assertEquals(cartTagName, "ul");

        List<WebElement> cartListItems = cart.findElements(By.tagName("li"));
        Assert.assertEquals(cartListItems.size(), 1);
        String cartText = cart.getText();
        Assert.assertTrue(cartText.toLowerCase().contains(coatText));

        // rahul shetty advises using streams to check that the coat has been added to the cart
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3"));
        Assert.assertTrue(cartItems.size() > 0);
        WebElement h3ForCoat = cartItems.stream().filter(h3 -> h3.getText().equalsIgnoreCase(coatText))
                .findFirst()
                .orElse(null);
        Assert.assertTrue(h3ForCoat.getText().equalsIgnoreCase(coatText));

        // in rahul shetty's code he just checks for the presence of the item via a boolean check
        Boolean coatPresent = cartItems.stream().anyMatch(item -> item.getText().equalsIgnoreCase(coatText));
        Assert.assertTrue(coatPresent);

        // now checkout and assert you have landed on the checkout page
        driver.findElement(By.cssSelector(".subtotal button")).click();
        List<WebElement> paymentTitles = wait.until(
            ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector(".payment__title")))
        );
        Assert.assertTrue(paymentTitles.size() > 0);

        // this is going to be a large code section, much larger than what Rahul Shetty requires, I want
        // to fill out as many items on the checkout page as I can.

        // first get all the titles then we can get the adjacent elements
        List<WebElement> checkoutTitles = driver.findElements(By.className("title"));
        Assert.assertTrue(checkoutTitles.size() > 0);
        // get the credit card title
        WebElement creditCardTitle = checkoutTitles.stream().filter(title ->
                title.getText().strip().equalsIgnoreCase("credit card number"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(creditCardTitle);
        WebElement creditCardInput = creditCardTitle.findElement(By.xpath("following-sibling::*"));
        creditCardInput.clear();
        Assert.assertTrue(creditCardInput.getAttribute("value").equals(""));
        creditCardInput.sendKeys("4542 9931 9292 2293");

        // get the Select lists for the expiration date
        List<WebElement> expirationDate = driver.findElements(By.cssSelector("select.input"));
        Assert.assertEquals(expirationDate.size(), 2);
        Select expirationMonth = new Select(expirationDate.get(0));
        Select expirationDay = new Select(expirationDate.get(1));

        expirationMonth.selectByIndex(5);
        expirationDay.selectByIndex(20);

        String selectedMonth = expirationMonth.getFirstSelectedOption().getText();
        String selectedDay = expirationDay.getFirstSelectedOption().getText();

        Assert.assertTrue(selectedMonth.equals("06"));
        Assert.assertTrue(selectedDay.equals("21"));

        // new find the CVV number and enter it
        WebElement CVVTitle = checkoutTitles.stream().filter(title ->
                title.getText().strip().equalsIgnoreCase("cvv code ?"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(CVVTitle);
        WebElement CVVInput = driver.findElement(with(By.tagName("input")).below(CVVTitle));
        CVVInput.sendKeys("456");
        Assert.assertTrue(CVVInput.getAttribute("value").equals("456"));

        // find and enter the name on the card
        WebElement nameOnCardTitle = checkoutTitles.stream().filter(title ->
                title.getText().strip().equalsIgnoreCase("name on card"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(nameOnCardTitle);
        WebElement nameOnCardInput = driver.findElement(with(By.tagName("input")).below(nameOnCardTitle));
        nameOnCardInput.sendKeys("Elmer Fudd");
        Assert.assertTrue(nameOnCardInput.getAttribute("value").equalsIgnoreCase("Elmer Fudd"));

        // verify Elmer Fudd's email is entered
        List<WebElement> userInputs = driver.findElements(By.cssSelector(".user__name input"));
        WebElement emailInput = userInputs.get(0);
        Assert.assertTrue(emailInput.getAttribute("value").equalsIgnoreCase(email));

        // enter and verify country
        WebElement countryInput = userInputs.get(1);
        countryInput.sendKeys("United");
        String xpathToFindUnitedStates = "//section[contains(@class, 'list-group')]//span[text() = ' United States']";
        WebElement countrySelected = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToFindUnitedStates))
        );
        countrySelected.click();
        Assert.assertTrue(countryInput.getAttribute("value").equalsIgnoreCase("United States"));
        countryInput.clear();

        // enter and verify country with Actions class
        Actions setCountryAction = new Actions(driver);
        setCountryAction.sendKeys(
                driver.findElement(By.cssSelector("[placeholder='Select Country']")), "United States")
                .build()
                .perform();
        countrySelected = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToFindUnitedStates))
        );
        countrySelected.click();
        Assert.assertTrue(countryInput.getAttribute("value").equalsIgnoreCase("United States"));

        // place the order and assert that on the confirmation page
        String titleText = "Thankyou for the order.";
        driver.findElement(By.cssSelector("div.actions a")).click();
        WebElement confirmationPageTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(confirmationPageTitle.getText().equalsIgnoreCase(titleText));
    }
}
