import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class AbstractWebDriver {
    WebDriver driver;
    @BeforeTest
    public void setupDriver(){
        ////Using remote webdriver/////
        //DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setBrowserName("chrome");
        //WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        ////Using WebDriverManager////
        //webDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();


        ////Using Local ChromeDriver////
        System.setProperty("webdriver.chrome.driver","C:/ChromeDriver/chromedriver.exe");
        driver = new ChromeDriver();
    }
    @AfterTest
    public void quitDriver(){
        driver.quit();
    }

    public void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
