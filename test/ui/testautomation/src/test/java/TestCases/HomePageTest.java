package TestCases;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import smallwindow.pages.HomePage;
import smallwindow.utils.WebDriverHelper;

public class HomePageTest {

    public static Path CurrentDirectory = null;
    public Logger logger = LogManager.getLogger(HomePageTest.class);
    private HomePage homepage = null;

    @Before
    public void init(){

        WebDriverHelper.setDriverLocation("C:\\SeleniumDriver");

        if(CurrentDirectory == null) {
            CurrentDirectory = Paths.get("").toAbsolutePath();

            PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.properties");
        }

        PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.properties");
    }

    @Test
    public void verifyHomePageLaunched() throws MalformedURLException{

        logger.info("Navigating to home page");

        WebDriverHelper.open();

        WebDriverHelper.getDriver().navigate().to("http://localhost:8080/");

        homepage = new HomePage(WebDriverHelper.getDriver());

        String pageTitle = homepage.navBarElement().getText().toString();

        //verify the title of the page
        assertEquals("Smallwindow21", pageTitle);
    }

    @After
    public void teardown(){
        WebDriverHelper.close(false);
    }
}
