package smallwindow.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import smallwindow.utils.WebDriverHelper;

/**
 * Base Page functionality
 * @author Sharon
 */
public class BasePage {

    public WebDriver driver;
    public Logger logger;

    public BasePage(WebDriver driver){
        super();
        this.driver = driver;

        logger = LogManager.getLogger(BasePage.class);
    }

    /**
     * returns a webelement and waits up to 10 seconds
     * @param by
     * @return 
     */
    public WebElement waitforElement(By by){
        return waitForElement(by, 10);
    }

    /**
     * returns a webelement and waits a set time
     * @param by
     * @param secondsToWait
     * @return 
     */
    public WebElement waitForElement(By by, int secondsToWait){
        WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}