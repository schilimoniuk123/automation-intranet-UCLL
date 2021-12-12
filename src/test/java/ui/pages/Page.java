package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class Page {
    private static WebDriver driver;
    String path = "https://intranet.ucll.be";

    public static void initDriver () {
        System.setProperty("webdriver.chrome.driver", "src/test/chromedriver.exe");
        if(driver == null) {
            driver = new ChromeDriver();
        }
    }

    public static void quitDriver() {
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }
    public static WebDriver getDriver() {
        return driver;
    }

    public String getPath() {
        return path;
    }
}