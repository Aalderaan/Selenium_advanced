package com.herokuapp.theinternet.base;

import java.util.HashMap;
import java.util.Map;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class BrowserDriverFactory {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() {
        // Create driver
        log.info("Create driver: " + browser);

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                WebDriverManager.chromedriver().setup();//.chromedriver().driverVersion("85.0.4183.38").setup();
                //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                //System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                WebDriverManager.edgedriver().setup();
                //System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new EdgeDriver(edgeOptions));
                break;


            case "chromeheadless":
                WebDriverManager.chromedriver().setup();//System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                ChromeOptions chromeOptions_ = new ChromeOptions();
                chromeOptions_.addArguments("--headless");
                driver.set(new ChromeDriver(chromeOptions_));
                break;

            case "firefoxheadless":
                WebDriverManager.firefoxdriver().setup();//System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions_ = new FirefoxOptions();
                firefoxOptions_.setBinary(firefoxBinary);
                driver.set(new FirefoxDriver(firefoxOptions_));
                break;

            case "phantomjs":
                System.setProperty("phantomjs.binary.path", "src/main/resources/phantomjs.exe");
                driver.set(new PhantomJSDriver());
                break;


            case "htmlunit":
                driver.set(new HtmlUnitDriver());
                break;

            default:
                ChromeOptions chromeOption = new ChromeOptions();
                WebDriverManager.chromedriver().setup();//.chromedriver().driverVersion("85.0.4183.38").setup();
                //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver(chromeOption));
                break;

                /*System.out.println("Do not know how to start: " + browser + ", starting chrome.");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver());
                break;*/
        }

        return driver.get();
    }
    public WebDriver createChromeWithMobileEmulation(String deviceName) {
        log.info("Starting driver with " + deviceName + " emulation]");
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", deviceName);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver.set(new ChromeDriver(chromeOptions));
        return driver.get();
    }
}