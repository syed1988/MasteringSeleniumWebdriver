package com.webdriver.selenium.mastering;

import com.webdriver.selenium.mastering.config.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

import static com.webdriver.selenium.mastering.config.DriverType.FIREFOX;
import static com.webdriver.selenium.mastering.config.DriverType.PHANTOMJS;
import static com.webdriver.selenium.mastering.config.DriverType.valueOf;

public class WebDriverThread {

    private WebDriver webDriver;
    private DriverType selectDriverType;

    private final DriverType defaultDriverType = FIREFOX;
    private final String browser = System.getProperty("browser").toUpperCase();

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");

    public WebDriver getDriver() throws Exception{
        if (webDriver == null){
           selectDriverType = determineEffectiveDriverType();
           DesiredCapabilities desiredCapabilities =
                   selectDriverType.getDesiredCapabilities();
            instantiateWebDriver(desiredCapabilities);

        }
        return webDriver;
    }
    public void quitDriver(){
        if (webDriver != null){
            webDriver.quit();
        }
    }

    private DriverType determineEffectiveDriverType(){
        DriverType driverType = defaultDriverType;
        try {
            driverType = valueOf(browser);
        }catch (IllegalArgumentException ignored){
            System.err.println("Unknown webDriver specified, defaulting to '" + driverType + "'...");
        }catch (NullPointerException ignored){
            System.err.println("Unknown webDriver specified, defaulting to '" + driverType + "'...");
        }
        return driverType;
    }
    private void instantiateWebDriver(DesiredCapabilities desiredCapabilities) throws MalformedURLException {
        System.out.println(" ");
        System.out.println("Current Operating System: "+ operatingSystem);
        System.out.println("Current Architecture: "+ systemArchitecture);
        System.out.println("Current Browser Selection: " + selectDriverType);
        System.out.println(" ");
        webDriver = selectDriverType.getWebDriverObject(desiredCapabilities);
    }
}
