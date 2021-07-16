package smallwindow.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DrbgParameters.Capability;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.*;

public final class WebDriverHelper{

    public static String driverLocation = null;

    public static WebDriver driver = null;

    public static Logger logger = LogManager.getLogger(WebDriverHelper.class);

    private static Path CurrentDirectory = null;

    public static void setDriverLocation(String location){
        driverLocation = location;
    }

    public static String getDriverLocation(){
        return driverLocation != null ? driverLocation : Paths.get("").toAbsolutePath().toString();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static void setDriver(WebDriver driver){
        WebDriverHelper.driver = driver;
    }

    protected static ChromeOptions optionsGC(Boolean isHeadless){
        ChromeOptions options = new ChromeOptions();

        options.setAcceptInsecureCerts(true);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        options.setUnhandledPromptBehaviour((UnexpectedAlertBehaviour.ACCEPT));
        options.addArguments("disable-infobars");

        if(isHeadless){
            List<String> args = new ArrayList<String>();
            args.add(0, "--headless");
            args.add(1, "--disable-gpu");
            args.add(2, "--window-size=1600x960");

            options.addArguments(args);
        }

        System.setProperty("webdriver.chrome.driver", getDriverLocation() + "\\chromedriver.exe");

        return options;
    }

    public static WebDriver open() throws MalformedURLException{
        
        PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.properties");

        WebDriver driver = null;

        driver = new ChromeDriver(optionsGC(false));
        //driver = new RemoteWebDriver(new URL("http://localhost:8080/wd/hub"), optionsGC(false));
        logger.info("Setting up browser: Chrome");

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        setDriver(driver);

        return driver;
    }

    public static WebDriver open(String url) throws MalformedURLException{
        WebDriver driver = open();

        logger.info("Navigating to url: " + url);

        driver.navigate().to(url);

        return driver;
    }

    public static RemoteWebDriver getRemoteWebDriver(WebDriver driver){
        return((RemoteWebDriver) driver);
    }

    // public static void close(WebDriver driver){
    //     if (driver != null){
    //         if(getRemoteWebDriver(driver).getSessionID() != null){
    //             driver.close();
    //             driver.quit();
    //         }
    //     }
    // }

    public static void close(boolean CallingfromBaseSuite){
        if(getDriver() != null){
            if(getRemoteWebDriver(driver).getSessionId() != null){
                driver.close();
                driver.quit();

                if(CallingfromBaseSuite){
                    logger.info("Cleaning up selenium webdriver for the test");
                }
            }
        }
    }

    public static String screenShot(){
        if(CurrentDirectory == null){
            CurrentDirectory = Paths.get("").toAbsolutePath();
        }

        File screenShot = getScreenshot(driver);
        if(screenShot != null){
            String screenShotName = getScreenshotName("Screenshot", CurrentDirectory.toString());

            File currPath = new File(screenShotName);
            try {
                
                FileHandler.copy(screenShot, currPath);
                logger.info("Screenshot saved to: " + currPath.toString());
            } catch (IOException e){
                logger.error("Failed to save screenshot : " + e.getMessage());
            }
            return currPath.toString();
        } 
        
        return null;
    }

    public static File getScreenshot(WebDriver driver){
        File screen_shot = null;

        try{
            if(driver == null){
                return screen_shot;
            }
            logger.info("Taking screenshot of browser titled: '" + driver.getTitle() + "', URL: " + driver.getCurrentUrl());
            screen_shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e){
            logger.error("Failed to take screenshot: " + e.getMessage());
        }

        return screen_shot;
    }

    public static String getScreenshotName(String screenShotName, String screenshotPath){
        boolean existing = true;
        int extension = 2;
        String originalname = screenShotName;

        while (existing){
            //while file exists, check for new file
            if(new File(screenshotPath+ File.separator + screenShotName + ".png").exists()){
                screenShotName = originalname + String.valueOf(extension);
                extension++;
            }else {
                existing = false;
            }
        }
        return screenShotName + ".png";
    } 
}