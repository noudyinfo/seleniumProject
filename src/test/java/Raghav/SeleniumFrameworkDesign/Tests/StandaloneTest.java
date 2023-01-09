package Raghav.SeleniumFrameworkDesign.Tests;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StandaloneTest {
	
	public static void main (String[] args) throws InterruptedException
	{
		String productName = "ZARA COAT 3";
		WebDriver driver = new FirefoxDriver();
		// Implicit Wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("noudyinfo@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Testing@123");
		driver.findElement(By.id("login")).click();

		//Putting explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		List<WebElement> AllProduts = driver.findElements(By.cssSelector(".mb-3"));
		List<WebElement> AllProductName = driver.findElements(By.cssSelector(".card-body h5 b"));
		
		//Way 1 using Stream
		WebElement prod = AllProduts.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//wait untill toast message appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//wait for the loader do disappear
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		// Get All items from cart in list
		List<WebElement> cartProducts = driver.findElements(By.xpath("//*[@class='cartSection']/h3"));
		//checking if added product name macthes from the product list
		boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		Thread.sleep(5000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".totalRow button")));
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//	Selecting country name from dropdown
		Actions act = new Actions (driver);
		act.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
		
		// scrolling before we click on placeOrder button
		Thread.sleep(2000);
		JavascriptExecutor jsExe = (JavascriptExecutor) driver;
		jsExe.executeScript("window.scrollBy(0, 5000)");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String ConfirmationMessage = driver.findElement(By.xpath("//td/h1[@class='hero-primary']")).getText();
		Assert.assertTrue(ConfirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
//		driver.quit();
		
		
		

		//Way 2 using regular logic
//		for (int i=0; i<AllProduts.size(); i++)
//		{
//			String eachProductName = AllProductName.get(i).getText();
//			System.out.println("eachProductName== "+eachProductName);
//			if (eachProductName.equalsIgnoreCase("ZARA COAT 3"))
//			{
//				driver.findElement(By.cssSelector("button[class='btn w-10 rounded']")).click();
//			}
//		}
	}

}
