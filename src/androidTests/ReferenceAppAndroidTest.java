package androidTests;

import java.util.List;

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
import pageobjects.InsurancePicker;
import pageobjects.Home;
import pageobjects.InsuranceType;
import pageobjects.MotorCycleComprehensivePage;
import util.Config;
import util.Config.ConfigProps;
import util.Config.GroupProps;
import util.Config.ParamProps;
import util.Config.StaticProps;


@SuppressWarnings("unused")
public class ReferenceAppAndroidTest extends BaseTest{
	
	private Home home;
	private InsurancePicker appPicker;
	private InsuranceType mailComposePage;
	private DriverManager driverManager;
	private InsurancePicker insurancePicker;
	private InsuranceType insuranceType;
	private MotorCycleComprehensivePage motorCycleComprehensivePage;
	
	@BeforeMethod(alwaysRun = true)
	public void setupTest() throws Exception {
		driverManager = new DriverManager();
		home = new Home();
		insurancePicker = new InsurancePicker();
		mailComposePage = new InsuranceType();
		insuranceType = new InsuranceType();
		motorCycleComprehensivePage = new MotorCycleComprehensivePage();
		}

	
	@Test(groups = {GroupProps.DEBUG}, description = "Landing Page Test")
	public  void ReferenceAndroidAppTest() {
		
		
		home.pause(ConfigProps.APP_LOADING_TIME);
	
		home.homeBtn().waitForVisible().tap();
		
		insurancePicker.insurancePlansBtn().waitForVisible();
		
		insurancePicker.premiumCalBtn().waitForVisible().tap();
		
		insuranceType.motorCycleBtn().waitForVisible().tap();

		motorCycleComprehensivePage.sumInsuredTxtBox().waitForVisible().tap().type("50000");
		
		motorCycleComprehensivePage.ccTxtBox().waitForVisible().tap().type("160");
		
		motorCycleComprehensivePage.ageofMCTxtBox().waitForVisible().tap().type("2");
		
		motorCycleComprehensivePage.ageofMCTxtBox().hideKeyboard();
		
		motorCycleComprehensivePage.ageofMCTxtBox().scrollIntoView("CALCULATE");
		
		motorCycleComprehensivePage.calculateBtn().waitForVisible().tap();
		
		home.pause(ConfigProps.APP_LOADING_TIME);
		
		System.out.println("SUCCESS");
	}
	
	
}
