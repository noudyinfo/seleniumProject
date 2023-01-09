package Raghav.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Raghav.SeleniumFrameworkDesign.pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver () throws IOException
	{
		// Properties Class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Raghav//Resources//GlobalData.properties");
		prop.load(fis);

		// Setting up the browser type when we run using maven command
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		//prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
		}
		else if (browserName.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		// Implicit Wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public String getScreenshot (String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File (System.getProperty("user.dir")+ "//reports//" +testCaseName+ ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+ "//reports//" +testCaseName+ ".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplicaion() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown ()
	{
		driver.close();
	}
	
	public List<HashMap<String, String>> getJsonDataToMap (String filePath) throws IOException
	{
		// Read Json file from local path
		String jsonContent = FileUtils.readFileToString(
				new File(filePath), StandardCharsets.UTF_8);
		
		//String to Hashmap : We need to add the dependency of Jackson Databind to pon.xml file
		ObjectMapper mapper = new ObjectMapper ();
		List<HashMap<String, String>> data = mapper.readValue(
				jsonContent, new TypeReference<List<HashMap<String, String>>>(){
			});
		return data;
		
	}

}
