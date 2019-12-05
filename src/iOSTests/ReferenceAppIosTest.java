package iOSTests;

import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.BaseTest;
import driverutils.DriverManager;
import io.appium.java_client.MobileElement;
import pageobjects.Home;
import util.Config;
import util.Config.ConfigProps;
import util.Config.GroupProps;
import util.Config.ParamProps;
import util.Config.StaticProps;


@SuppressWarnings("unused")
public class ReferenceAppIosTest extends BaseTest{
	
	private Home home;
	
	@BeforeMethod(alwaysRun = true)
	public void setupTest() throws Exception {
		home = new Home();
	}

	
	@Test(groups = {GroupProps.DEBUG}, description = "Landing Page Test")
	public  void landingPageTest() {
		
		home.pause(ConfigProps.APP_LOADING_TIME);
		
//		home.dynamicTxt().assertTrue(home.uiBtn().waitForVisible().hasAttribute("name", "Button"),
//				"Button Text is displayed as Button", "Dynamic Text is NOT displayed as Button");
//		
//		
//		home.dynamicTxt().assertTrue(home.dynamicTxt().waitForVisible().hasAttribute("value", "Hello"),
//				"Dynamic Text is displayed as Hello", "Dynamic Text is NOT displayed as Hello");
		
	}
	
	@Test(groups = {GroupProps.DEBUG}, description = "Orientation Test")
	public  void orientationTest(){
		

//		home.dynamicTxt().assertTrue(home.uiBtn().getOrientation().equalsIgnoreCase(ScreenOrientation.PORTRAIT.toString()),
//				"Opening App Orientation is Portrait", "Opening App Orientation is Landscape");
//		
//		home.uiBtn().rotateScreen(ScreenOrientation.LANDSCAPE);
//		
//		home.dynamicTxt().assertTrue(home.uiBtn().getOrientation().equalsIgnoreCase(ScreenOrientation.LANDSCAPE.toString()),
//				"App Orientation is set to Landscape", "App Orientation remains as Portrait");
		
	}
	
	@Test(groups = {GroupProps.DEBUG}, description = "Button Validation Test")
	public  void buttonValidationTest(){
		
//		home.pause(ConfigProps.APP_LOADING_TIME);
//		
//		home.uiBtn().waitForVisible().tap();
//		
//		home.dynamicTxt().assertFalse(home.dynamicTxt().waitForVisible().getElementAttribute("value").equals("Hello"), 
//				"Dynamic Text is no longer displayed as Hello", "Dynamic Text is still displayed as Hello");
//		
//		String randomValue = home.dynamicTxt().waitForVisible().getvalue();
//		
//		char currency = randomValue.trim().charAt(0);
//
//		home.dynamicTxt().assertTrue(Character.toString(currency).equals("€"),
//				"Currency is Euro", "Currency is Not in Euro");
//		
//		String randomNumbers = randomValue.substring(2).replaceAll("[^\\d]", "").trim();
//		
//		home.dynamicTxt().assertTrue(randomNumbers.matches("\\d+"), "Currency Value is " + randomNumbers,
//				"Invalid Currency value");
		
	}


}
