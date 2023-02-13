package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetWebDriverLocation {
  private static final String chromeDriverProperty = "webdriver.chrome.driver";
  private static final String webDriversFolderPC = "C:\\Program Files\\WebDrivers\\";
  private static final String chromeDriverWindows = "chromedriver.exe";

  private static final String getWebDriversFolderMac = "/usr/local/bin/";
  private static final String chromeDriverMac = "chromedriver";

  public static void setDriverLocationAndDriverSystemProperty() {
    String windowsOSPattern = "Windows";
    Pattern regex = Pattern.compile(windowsOSPattern);
    String os = System.getProperty("os.name");
    Matcher matchWindows = regex.matcher(os);

    if (matchWindows.find()) {
      System.setProperty(chromeDriverProperty, webDriversFolderPC + chromeDriverWindows);
    } else {
      System.setProperty(chromeDriverProperty, getWebDriversFolderMac + chromeDriverMac);
    }
  }
}
