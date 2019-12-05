package driverutils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import report.ExtentTestManager;
import util.Config;

public class MobileInteract {

	private AppiumDriver<MobileElement> driver;
	private Duration timeout = Duration.ofSeconds(10);
	@SuppressWarnings("unused")
	private int timeOutInt = 0;
	@SuppressWarnings("unused")
	private Integer pollingTime;
	@SuppressWarnings("unused")
	private static Integer smallPause = 1000;
	@SuppressWarnings("unused")
	private static Integer minorPause = 500;

	// locator data
	private By locator;
	@SuppressWarnings("unused")
	private HashMap<String, String> locatorData;
	private String simpleName;

	// element data
	private Boolean elementPresent;
	private Boolean elementVisible;
	private MobileElement mobileElement;
	private MobileElement MobileElement;
	private MobileElement mobileWebElement;
	private List<MobileElement> allMobileWebElements;
	private List<MobileElement> allChildElements;
	private Boolean hasAttribute;

	public MobileInteract(By locator, HashMap<String, String> locatorData) {
		driver = DriverManager.getAppiumDriver();
		timeOutInt = Config.getMaxWaitTime();
		pollingTime = Config.getPollingTime();
		this.locator = locator;
		this.locatorData = locatorData;
		if (locator != null) {
			simpleName = locatorData.get("SimpleName");
		}
	}

	public MobileInteract setTimeout(Integer timeout) {
		this.timeOutInt = timeout;
		return this;
	}

	public MobileInteract setPollingTime(Integer pollingTime) {
		this.pollingTime = pollingTime;
		return this;
	}

	public MobileElement getMobileElement() {
		return mobileElement;
	}

	public IOSElement getIOSElement() {
		return (IOSElement) mobileElement;
	}

	public AndroidElement getAndroidElement() {
		return (AndroidElement) mobileElement;
	}

	public By getElementLocator() {
		return locator;
	}

	public String getElementSimpleName() {
		return simpleName;
	}

	public MobileElement getMobileWebElement() {
		return mobileWebElement;
	}

	public List<MobileElement> getAllMobileElements() {
		return allMobileWebElements;
	}

	public List<MobileElement> getAllChildMobileElements() {
		return allChildElements;
	}

	/**********************************************************************************************
	 * Pauses the test action.
	 * 
	 * @param waitTime
	 *            - {@link Integer} - The amount of time in milliseconds to pause.
	 ***********************************************************************************************/
	public MobileInteract pause(Integer waitTime) {
		System.out.println("Pause for '" + waitTime + "' milliseconds.");
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ExtentTestManager.getTest().log(Status.INFO, "Pause for '" + waitTime + "' milliseconds");
		
		return this;
	}


	// ELEMENT PRESENCE
	/**********************************************************************************************
	 * Determines if an element is present or not.
	 ***********************************************************************************************/
	public Boolean isPresent() {
		elementPresent = false;
		try {
			MobileElement = driver.findElement(locator);
			elementPresent = true;
		} catch (Exception e) {
			// element is not present
		}

		System.out.println("Is the '" + simpleName + "' element present: " + elementPresent.toString());
		return elementPresent;
	}


	/**********************************************************************************************
	 * Determines if an element is visible or not.
	 ***********************************************************************************************/
	public Boolean isVisible() {
		elementPresent = false;
		elementVisible = false;
		try {
			MobileElement = driver.findElement(locator);
			elementPresent = true;
			elementVisible = MobileElement.isDisplayed();
		} catch (Exception e) {
			// element is not present
		}

		System.out.println("Is the '" + simpleName + "' element visible: " + elementVisible.toString());
		return elementVisible;
	}


	// ATTRIBUTE PRESENCE
	/**********************************************************************************************
	 * Determines if an element has a specific attribute value or not.
	 * 
	 * @param attribute
	 *            - {@link String} - The specific attribute type to evaluate.
	 * @param attributeValue
	 *            - {@link String} - The value of the attribute to evaluate.
	 ***********************************************************************************************/
	public Boolean hasAttribute(String attribute, String attributeValue) {
		
		hasAttribute = MobileElement.getAttribute(attribute).equals(attributeValue);
		
		String msg = "The '" + simpleName + "' element has attribute '" + attribute + "' with value " + "'"
				+ attributeValue + "': " + hasAttribute.toString();
		System.out.println(msg);
		
		if(hasAttribute) {
			ExtentTestManager.getTest().log(Status.PASS, msg);
		} else {
			Assert.fail("the '"+ simpleName + "' element does not have the attribute " + attribute + " with value " + attributeValue);
		}
		return hasAttribute;
	}

	// ELEMENT WAITS
	/**********************************************************************************************
	 * Waits for an element to be present before timing out.
	 ***********************************************************************************************/
	public MobileInteract waitForPresent() {
		System.out.println("Verify the '" + simpleName + "' element is present.");
		this.byWait(locator).ignoring(WebDriverException.class)
				.withMessage(simpleName + " element with locator '" + locator.toString() + "' is not present.")
				.until(new Function<By, Boolean>() {
					@Override
					public Boolean apply(final By loc) {
						elementPresent = false;
						MobileElement = driver.findElement(loc);
						elementPresent = true;
						return elementPresent;
					}
				});
		return this;
	}


	/**********************************************************************************************
	 * Waits for an element to be present and visible before timing out.
	 ***********************************************************************************************/
	public MobileInteract waitForVisible() {
		System.out.println("Verify the '" + simpleName + "' element is visible.");
		this.byWait(locator).ignoring(WebDriverException.class)
				.withMessage(simpleName + " element with locator '" + locator.toString() + "' is not visible.")
				.until(new Function<By, Boolean>() {
					@Override
					public Boolean apply(final By loc) {
						MobileElement = driver.findElement(loc);
						if(MobileElement.isDisplayed()) {
							
								ExtentTestManager.getTest().log(Status.PASS, "the '"+ simpleName + "' element is visible.");
						} else {
							Assert.fail("the '"+ simpleName + "' element is not visible.");
						}
						return MobileElement.isDisplayed();
					}
				});
		return this;
	}


	
	/**********************************************************************************************
	 * Clicks on an element.
	 * 
	 ***********************************************************************************************/
	public MobileInteract tap() {
		System.out.println("Tap on the '" + simpleName + "' element.");
		ExtentTestManager.getTest().log(Status.INFO, "Tap on the " + simpleName + "' element.");
		MobileElement.click();
		return this;
	}

	
	/**********************************************************************************************
	 * Get Attribute of an element.
	 * 
	 ***********************************************************************************************/
	public String getElementAttribute(String attribute) {
		System.out.println("Element '" + simpleName + " has attribute" + MobileElement.getAttribute(attribute).toString());
		ExtentTestManager.getTest().log(Status.INFO, "Element '" + simpleName + " has attribute" + MobileElement.getAttribute(attribute).toString());
		return MobileElement.getAttribute(attribute).toString();
		
	}

	/**********************************************************************************************
	 * Types a string into an element.
	 * @param inputString
	 ***********************************************************************************************/
	public MobileInteract type(String inputString) {
		System.out.println("Type " + inputString +  " in the '" + simpleName + "' element.");
		ExtentTestManager.getTest().log(Status.INFO, "Type " + inputString +  " in the '" + simpleName + "' element.");
		MobileElement.sendKeys(inputString);
		return this;
	}
	// initiate fluent waits
	private FluentWait<By> byWait(final By locator) {
		return new FluentWait<By>(locator).withTimeout(timeout).pollingEvery(timeout);
	}


	public MobileInteract verifyText(String text) {
		String elementtext = driver.findElement(locator).getText();
		if (elementtext.contains(text)) {
			System.out.println("Verified that welcome message text contains the given username text");
		}
		return this;
	}


	public String getTxt() {
		return driver.findElement(locator).getText();
	}

	public String getvalue() {
		return driver.findElement(locator).getAttribute("value");
	}

	public void assertTrue(boolean condition , String successMsg , String failureMsg) {
		
		if(condition) {
			System.out.println(successMsg);
			
			ExtentTestManager.getTest().log(Status.PASS, successMsg);
		} else {
			System.out.println(failureMsg);
			
			Assert.fail(failureMsg);
		}
		
	}
	
	public void assertFalse(boolean condition , String successMsg , String failureMsg) {
		
		if(!condition) {
			System.out.println(successMsg);
			
			ExtentTestManager.getTest().log(Status.PASS, successMsg);
		} else {
			System.out.println(failureMsg);
			
			Assert.fail(failureMsg);
			
		}
		
	}
	

	public MobileInteract rotateScreen(ScreenOrientation orientation) {
		System.out.println("Rotating the screen to orientation : " + orientation.toString());
		ExtentTestManager.getTest().log(Status.INFO, "Rotating the screen to orientation : " + orientation.toString());
		driver.rotate(orientation);
		return this;
	}
	
	public String getOrientation() {
		String orientation = driver.getOrientation().toString();
		System.out.println("Current device orientation is : " + orientation);
		ExtentTestManager.getTest().log(Status.INFO, "Current device orientation is : " + orientation);
		return orientation;
	}
	
	public void scrollIntoView(String textMatcher) {
		
		DriverManager.getAndroidDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"" + textMatcher + "\").instance(0))");
		System.out.println("Scrolling to Mobile Element With Text " + textMatcher);
		ExtentTestManager.getTest().log(Status.INFO, "Scrolling to Mobile Element With Text " + textMatcher);
		
	}
}
