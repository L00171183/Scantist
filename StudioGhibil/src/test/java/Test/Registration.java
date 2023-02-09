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

public class Registration {

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
        driver.get("http://localhost:3000/register");
    }

    @Test(dataProvider = "registrationData")
    public void testRegistration(String name, String username, String password, String email) throws InterruptedException {
        // Enter the form data
    	driver.findElement(By.cssSelector("input:nth-child(2)")).sendKeys(name);
        // delay added after entering name
        Thread.sleep(random.nextInt(1000) + 500);
    	driver.findElement(By.cssSelector("input:nth-child(4)")).sendKeys(email);
        // delay added after entering email
        Thread.sleep(random.nextInt(1000) + 500);
        driver.findElement(By.cssSelector("input:nth-child(6)")).sendKeys(username);
        // delay added after entering user name
        Thread.sleep(random.nextInt(1000) + 500);
        driver.findElement(By.cssSelector("input:nth-child(8)")).sendKeys(password);
        // delay added after entering password
        Thread.sleep(random.nextInt(1000) + 500);

        // Submit the form
        driver.findElement(By.cssSelector("input:nth-child(10)")).click();
        // delay added after registration
        Thread.sleep(random.nextInt(5000) + 500);
        
        // Verify the expected result
        String actualResult = driver.findElement(By.cssSelector(".message")).getText();
        System.out.println("actualResult: "+actualResult);
        Thread.sleep(random.nextInt(1000) + 500);
        Assert.assertEquals(actualResult, "username already exists in our database, please choose a different username");
        //Registration Successful
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() throws IOException, CsvException {
        // Read the data from the CSV file
    	CSVPath = System.getProperty("user.dir")+"\\src\\main\\resources\\TestData.csv";
        CSVReader reader = new CSVReader(new FileReader(CSVPath));
        List<String[]> data = reader.readAll();

        // Convert the data to a 2D Object array
        Object[][] registrationData = new Object[data.size()-1][4];
        for (int i = 1, j=0; i < data.size(); i++,j++) {
            registrationData[j][0] = data.get(i)[0];
            registrationData[j][1] = data.get(i)[1];
            registrationData[j][2] = data.get(i)[2];
            registrationData[j][3] = data.get(i)[3];
        }
        return registrationData;
    }
}