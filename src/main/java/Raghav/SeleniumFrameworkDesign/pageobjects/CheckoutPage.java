package Raghav.SeleniumFrameworkDesign.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Raghav.AbstractComponent.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	@FindBy (css = ".action__submit")
	WebElement submit;
	
	@FindBy (css = "[placeholder='Select Country']")
	WebElement country;
	
	@FindBy (xpath = "//button[contains(@class,'ta-item list-group-item ng-star-inserted')][2]")
	WebElement selectCountry;
	
	By results = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) throws InterruptedException
	{
		Actions act = new Actions (driver);
		act.sendKeys(country, countryName).build().perform();
		Thread.sleep(3000);
		waitForElementToAppear(By.cssSelector(".ta-results"));
		Thread.sleep(3000);
		selectCountry.click();
	}
	
	public ConfirmationPage submitOrder()
	{
		submit.click();
		return new ConfirmationPage(driver);
		
	}
	
	

}
