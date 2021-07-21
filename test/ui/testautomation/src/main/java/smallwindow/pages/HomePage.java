package smallwindow.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

/**
 * Home Page page objects
 * @author Sharon
 */
public class HomePage extends BasePage{

    public HomePage(WebDriver driver){
        super(driver);
    }

    /**
     * Gets Nav Bar Element
     * @return
     */
    public WebElement navBarElement(){
        return waitforElement(By.cssSelector(".navbar-title"));
    }

    /**
     * Gets Sign In Element
     * @return
     */
    public WebElement signInElement(){
        return waitforElement(By.linkText("sign in"));
    }
  
}