package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class LoginPage {

    private WebDriver driver;
    private String driverpath,CSVPath;
    private Random random = new Random();

    @BeforeMethod
    public void setUp() {
        // Set up the web driver
    //	driverpath = System.getProperty("user.dir")+"\\src\\main\\resources\\chromedriver.exe";
      //  System.setProperty("webdriver.chrome.driver", driverpath);
    	System.setProperty("webdriver.chrome.driver","D:\\Eclipse\\SeleniumFramework\\Drivers\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();

        // Navigate to the registration page
        driver.get("http://localhost:3000/login");
    }

    @Test(dataProvider = "loginData")
    public void testRegistration(String username, String password) throws InterruptedException {
        // Enter the form data
    	driver.findElement(By.cssSelector("input:nth-child(2)")).sendKeys(username);
        // delay added after entering username
        Thread.sleep(random.nextInt(1000) + 500);
    	driver.findElement(By.cssSelector("input:nth-child(4)")).sendKeys(password);
        // delay added after entering password
        Thread.sleep(random.nextInt(1000) + 500);

        // Submit the form
        driver.findElement(By.cssSelector("input:nth-child(6)")).click();
        // delay added after entering email
        Thread.sleep(random.nextInt(5000) + 500);
        
        // get the entire HTML source code of the current page
        String sourceCode = driver.getPageSource();
        // check if the word you're looking for is present in the source code
        Assert.assertTrue(sourceCode.contains("Welcome to the premium content."), "The word 'Welcome to the premium content.' is not present in the page source.");

    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException, CsvException {
        // Read the data from the CSV file
    	CSVPath = System.getProperty("user.dir")+"\\src\\main\\resources\\Login_TestData.csv";
        CSVReader reader = new CSVReader(new FileReader(CSVPath));
        List<String[]> data = reader.readAll();

        // Convert the data to a 2D Object array
        Object[][] loginData = new Object[data.size()-1][2];
        for (int i = 1, j=0; i < data.size(); i++,j++) {
        	loginData[j][0] = data.get(i)[0];
        	loginData[j][1] = data.get(i)[1];
        }
        return loginData;
    }
}