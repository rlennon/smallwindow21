package smallwindow.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public WebElement loginTitleElement(){
        return waitforElement(By.cssSelector("h1"));
    }

    public WebElement userNameInputElement(){
        return waitforElement(By.cssSelector("input#username"));
    }

    public WebElement passwordInputElement(){
        return waitforElement(By.cssSelector("input#password"));
    }

    public WebElement signInButtonElement(){
        return waitforElement(By.cssSelector(".btn-primary"));
    }

}