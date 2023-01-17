package com.example.webdrivermethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverMethods {
  public static void main(String[] args) {
    System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDrivers\\chromedriver.exe");
    WebDriver driver = new ChromeDriver();
    driver.get("https://rahulshettyacademy.com");
    String title = driver.getTitle();
    System.out.println(title);
    driver.close();
  }
}
