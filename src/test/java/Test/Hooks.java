package Test;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.FileReader;
import java.io.InvalidObjectException;
import java.util.Properties;

public class Hooks {
    static WebDriver driver;

    @Before
    public static void Beforemethod() throws Exception {
        String path = System.getProperty("user.dir");

        File file = new File(path + "\\Redbus.properties");
        FileReader read = new FileReader(file);
        Properties properties = new Properties();
        properties.load(read);

        String browser = properties.getProperty("browser");
        String app_url = properties.getProperty("app_url");

        if (browser.equals("edge")) {
            System.setProperty("webdriver.edge.driver", path + "\\msedgedriver.exe");
            driver = new EdgeDriver();
        } else if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");
            driver = new EdgeDriver();
        } else {
            throw new InvalidObjectException("given browser is not correct please check it once and try again");
        }
        driver.manage().window().maximize();
        driver.get(app_url);
    }

    @After
    public static void Aftermethod() {
        driver.quit();
    }

}
