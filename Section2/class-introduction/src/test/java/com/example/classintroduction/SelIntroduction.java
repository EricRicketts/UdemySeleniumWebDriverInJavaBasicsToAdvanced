package com.example.classintroduction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelIntroduction {

  public static void main(String[] args) {
// Windows webdriver
// Apple stopped supporting Safari on Windows after version 5.17 Safari is now on version 10, it is not worth the
// effort
//    System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDrivers\\chromedriver.exe");
//    System.setProperty("webdriver.edge.driver", "C:\\Program Files\\WebDrivers\\msedgedriver.exe");
//    System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\WebDrivers\\geckodriver.exe");


//    Mac webdrivers
//    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
//    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
//    System.setProperty("webdriver.edge.driver", "/usr/local/bin/msedgedriver");
//    System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");

//    WebDriver driver = new ChromeDriver();
//    WebDriver driver = new FirefoxDriver();
    WebDriver driver = new EdgeDriver();
//    WebDriver driver = new SafariDriver();
    driver.get("https://rahulshettyacademy.com");
    driver.close();
  }
}
