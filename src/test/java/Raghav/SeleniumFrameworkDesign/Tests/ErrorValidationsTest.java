package Raghav.SeleniumFrameworkDesign.Tests;

import java.io.IOException;
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
import org.testng.annotations.Test;

import Raghav.SeleniumFrameworkDesign.pageobjects.CartPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.LandingPage;
import Raghav.SeleniumFrameworkDesign.pageobjects.ProductCatalog;
import Raghav.TestComponents.BaseTest;
import Raghav.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest {
	
//	@Test (groups= {"ErrorHandling"})
	@Test (groups= {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void LoginErrorValidation () throws IOException, InterruptedException
	{
		String productName = "ZARA COAT 3";
		// Passing invalid credentials
		landingPage.loginApplication("noudyinfo66@gmail.com", "Testing@12366");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation () throws IOException, InterruptedException
	{
		String productName = "ZARA COAT 3";

		ProductCatalog productCatalog = landingPage.loginApplication("noudyinfo@gmail.com", "Testing@123");
		productCatalog.addProductToCart(productName);
		CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 3333");
		Assert.assertTrue(match);

	}

}
