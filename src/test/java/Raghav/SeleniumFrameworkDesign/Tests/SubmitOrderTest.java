package Raghav.SeleniumFrameworkDesign.Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Raghav.SeleniumFrameworkDesign.pageobjects.CartPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.LandingPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.OrderPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.ProductCatalog;
import Raghav.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
	
	String productName = "ZARA COAT 3";
	@Test(dataProvider="getData", groups="Purchase")
	// Way 1 using HashMap
	public void SubmitOrder (HashMap<String, String> input) throws IOException, InterruptedException

	//Way 2 using direct Object multi dimentional array - To retrieve
	//public void SubmitOrder (String email, String password, String productName) throws IOException, InterruptedException
	{
		ProductCatalog productCatalog = landingPage.loginApplication(input.get("email"), input.get("password"));
		productCatalog.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalog.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}
	
	
	@Test (dependsOnMethods= {"SubmitOrder"})
	public void OrderHistoryTest ()
	{
		ProductCatalog productCatalog = landingPage.loginApplication("noudyinfo@gmail.com", "Testing@123");
		OrderPage orderPage = productCatalog.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}
	
	
	//Way 1 using direct Object multi dimentional array
//	@DataProvider
//	public Object[][] getData ()
//	{
//		//return new Object[][] {{"noudyinfo@gmail.com", "Testing@123", "ZARA COAT 3"}, {"noudyinfo1@gmail.com", "Testing@123", "ADIDAS ORIGINAL"}};
//	}
	
	//Way 2 using Hashmap with multi-dimentional array
//	@DataProvider
//	public Object[][] getData ()
//	{
//		HashMap<String, String> map = new HashMap<String, String> ();
//		map.put("email", "noudyinfo@gmail.com");
//		map.put("password", "Testing@123");
//		map.put("productName", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String> ();
//		map1.put("email", "noudyinfo1@gmail.com");
//		map1.put("password", "Testing@123");
//		map1.put("productName", "ADIDAS ORIGINAL");
//		
//		return new Object [][] {{map}, {map1}};
//		
//	}
	
	// Way 3 using Jason file reader
	@DataProvider
	public Object[][] getData () throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//Raghav//Data//PurchaseOrder.json");
		return new Object [][] {{data.get(0)}, {data.get(1)}};
	}
		
}
