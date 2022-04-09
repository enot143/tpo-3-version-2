package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Init {
    public final Properties property = new Properties();
    public WebDriver driver;

    public Init() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/conf.properties");
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver initWebDriver() {
        if (property.getProperty("main.driver").equals("CHROME")) {
            System.setProperty("webdriver.chrome.driver", property.getProperty("webdriver.chrome.driver"));
            driver = new ChromeDriver();
        } else if (property.getProperty("main.driver").equals("FIREFOX")) {
            System.setProperty("webdriver.gecko.driver", property.getProperty("webdriver.gecko.driver"));
            File pathToBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(String.valueOf(pathToBinary));
            driver = new FirefoxDriver(firefoxOptions);
//            FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
//            FirefoxProfile firefoxProfile = new FirefoxProfile();
//            driver = new FirefoxDriver(ffBinary, firefoxProfile);
        }
        return driver;
    }
    public String getProperty(String name){
        return property.getProperty(name);
    }
}