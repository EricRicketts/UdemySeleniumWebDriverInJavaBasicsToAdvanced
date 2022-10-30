package com.example.classintroduction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SelIntroduction {

  public static void main(String[] args) {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
//    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
//    System.setProperty("webdriver.edge.driver", "/usr/local/bin/msedgedriver");
//    System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
    WebDriver driver = new ChromeDriver();
//    WebDriver driver = new FirefoxDriver();
//    WebDriver driver = new EdgeDriver();
//    WebDriver driver = new SafariDriver();
    driver.get("https://rahulshettyacademy.com");
  }
}
