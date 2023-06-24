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
import java.util.ArrayList;
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
        List<String> selectedProductTitles = new ArrayList<>();
        int cartCount = 0; // used to track how many products are bought
        // this is the cart button which takes the user to the cart page
        String cartButtonCss = "button[routerlink=\"/dashboard/cart\"]";
        // this is the label element that holds the car count, ie, how many products
        // the user has decided to buy
        String cartButtonLabelCss = "button[routerlink=\"/dashboard/cart\"] > label";
        String headingElementsForItemsSelectedOnMyCartPage = ".items h3";
        // find and enter login elements user email, password, and the login button
        driver.findElement(By.id("userEmail")).sendKeys(userEmail);
        driver.findElement(By.id("userPassword")).sendKeys(userPassword);
        driver.findElement(By.id("login")).click();

        // ensure you have landed on the main page by asserting on what appears to be the title element
        WebElement headingForPageTitle = driver.findElement(By.cssSelector("div.mt-1 > h3"));
        Boolean pageHeadingMainTextFound = wait.until(
                ExpectedConditions.textToBePresentInElement(headingForPageTitle, "AUTOMATION")
        );

        Assert.assertTrue(pageHeadingMainTextFound);

        // go through all the listed products select each one button.w-10 is the "Add to Cart" button
        // as each item is selected update the cart count and assert on the updated quantity
        List<WebElement> listedProducts = driver.findElements(By.cssSelector("div.card"));
        for(WebElement product : listedProducts) {
            // find the title of the selected product and store it in the product titles list
            String productTitle = product.findElement(By.tagName("b")).getText();
            selectedProductTitles.add(productTitle);

            // now add the product to the cart by clicking the Add To Cart button
            product.findElement(By.cssSelector("button.w-10")).click();
            // this is the text of the cart button label which shows the number of items selected to buy
            WebElement cartQuantity = driver.findElement(By.cssSelector(cartButtonLabelCss));
            cartCount += 1;
            Boolean cartCountUpdated = wait.until(
                    ExpectedConditions.textToBePresentInElement(cartQuantity, String.valueOf(cartCount))
            );
            Assert.assertTrue(cartCountUpdated);
        }

        // select the cart button to go to "my cart" page
        WebElement cartButton = driver.findElement(By.cssSelector(cartButtonCss));
        cartButton.click();

        // Look for the checkout button and assert on its presence
        WebElement checkoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Checkout')]")
                )
        );
        Assert.assertNotNull(checkoutButton);

        // Based on the videos by Rahul Shetty I need to verify the items selected to buy are
        // listed on the My Cart page

        // get all headings on My Cart page
        List<WebElement> myCartPageHeadings = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(headingElementsForItemsSelectedOnMyCartPage)
                )
        );

        // verify all the products in the My Cart page are those which have been selected by user
        for(WebElement heading : myCartPageHeadings) {
            Assert.assertTrue(selectedProductTitles.contains(heading.getText()));
        }

        // in this case I had to use JavascriptExecutor to manually scroll to the checkout button
        // as just clicking on it threw an exception, as the button was not in view when
        // originally clicked
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", checkoutButton);
        javascriptExecutor.executeScript("arguments[0].click()", checkoutButton);

        // assert I am on the checkout page
        WebElement paymentMethodElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),'Payment Method')]")
                )
        );

        Assert.assertNotNull(paymentMethodElement);
    }
}