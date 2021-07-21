package smallwindow.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

/**
 * Login Page Objects
 * @author Sharon
 */
public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }

    /**
     * Get Login Page Element
     * @return 
     */
    public WebElement loginTitleElement(){
        return waitforElement(By.cssSelector("h1"));
    }

    /**
     * Get User Name Input Element
     * @return
     */
    public WebElement userNameInputElement(){
        return waitforElement(By.cssSelector("input#username"));
    }

    /**
     * Get Password Input Element
     * @return
     */
    public WebElement passwordInputElement(){
        return waitforElement(By.cssSelector("input#password"));
    }

    /**
     * Get Sign In Button Element
     * @return
     */
    public WebElement signInButtonElement(){
        return waitforElement(By.cssSelector(".btn-primary"));
    }

}