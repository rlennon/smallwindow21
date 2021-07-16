package smallwindow.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class HomePage extends BasePage{

    public HomePage(WebDriver driver){
        super(driver);
    }

    //list of page objects and methods
    public WebElement NavBarElement(){
        return waitforElement(By.cssSelector(".navbar-title"));
    }

    public WebElement signInElement(){
        return waitforElement(By.linkText("sign in"));
    }

    
}