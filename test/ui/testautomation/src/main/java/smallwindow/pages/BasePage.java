package smallwindow.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import smallwindow.utils.WebDriverHelper;

public class BasePage {

    public WebDriver driver;
    public Logger logger;

    public BasePage(WebDriver driver){
        super();
        this.driver = driver;

        logger = LogManager.getLogger(BasePage.class);
    }

    // return webElement by and wait up to 10 seconds
    public WebElement waitforElement(By by){
        return waitForElement(by, 10);
    }

    // return webElement by and set wait time
    public WebElement waitForElement(By by, int secondsToWait){
        WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}