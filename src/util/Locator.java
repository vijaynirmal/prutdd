package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.MobileBy;
import util.Config.ConfigProps;
import util.Config.StaticProps;

public class Locator {

	public HashMap<String, String> locatorData;
	private String elementClassName;
	private String elementName;

	public Locator() {
		locatorData = new HashMap<>();
	}

	private JSONObject getElementJSON() {
		String elementFileName = ConfigProps.ELEMENT_FILE_PATH + elementClassName + ".json";

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(elementFileName));
		} catch (FileNotFoundException e) {
			Assert.fail("No Element File found.", e);
		} catch (IOException e) {
			Assert.fail("Failed to query Class Element File '" + elementClassName + "' " + "with element '"
					+ elementName + "'.", e);
		} catch (ParseException e) {
			Assert.fail("Failed to query Class Element File '" + elementClassName + "' " + "with element '"
					+ elementName + "'.", e);
		}
		return (JSONObject) jsonObject.get(elementName);
	}

	private By parseLocator(String inText) {
		String locatorString = "";
		String simpleName = "";
		By locator = null;
		try {
			JSONObject elementObject = getElementJSON();
			simpleName = (String) elementObject.get("SimpleName");
			JSONObject osObject = (JSONObject) elementObject.get(TestRun.getMobileOS().value());

			if (osObject.containsKey(StaticProps.PHONE) && TestRun.isPhone()) {
				locatorString = (String) osObject.get(StaticProps.PHONE);
			} else if (osObject.containsKey(StaticProps.TABLET) && TestRun.isTablet()) {
				locatorString = (String) osObject.get(StaticProps.TABLET);
			} else {
				locatorString = (String) osObject.get(StaticProps.ALL_DEVICES);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error on JSON query.", e);
		}

		String locatorArr[] = locatorString.split("::");
		String locatorType = locatorArr[0];
		String locatorDef = locatorArr[1];

		locatorData.put("SimpleName", simpleName);

		switch (locatorType) {
		case StaticProps.LOCATOR_ANDROID_UIAUTOMATOR:
			locator = MobileBy.AndroidUIAutomator(locatorDef);
			break;

		case StaticProps.LOCATOR_XPATH:
			locator = MobileBy.xpath(locatorDef);
			break;

		case StaticProps.LOCATOR_CSS:
			locator = MobileBy.cssSelector(locatorDef);
			break;

		case StaticProps.LOCATOR_ACCESSIBILITY_ID:
			locator = MobileBy.AccessibilityId(locatorDef);
			break;

		case StaticProps.LOCATOR_LINK_TEXT:
			locator = MobileBy.linkText(locatorDef);
			break;

		case StaticProps.RESOURCE_ID:
			locator = MobileBy.id(locatorDef);
			break;

		case StaticProps.LOCATOR_CLASS_NAME:
			locator = MobileBy.className(locatorDef);
			break;

		case StaticProps.LOCATOR_NAME:
			locator = MobileBy.name(locatorDef);
		}

		if (locator == null) {
			Assert.fail("No matching locator found for Class Element '" + elementClassName + "' " + "with element '"
					+ elementName + "'.");
		}
		return locator;
	}

	public By getLocator() {
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		int startingIndex = className.lastIndexOf(".");
		elementClassName = className.substring(startingIndex, className.length()).replace(".", "");
		elementName = Thread.currentThread().getStackTrace()[2].getMethodName();
		return parseLocator(null);
	}

	public By getLocator(String inText) {
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		int startingIndex = className.lastIndexOf(".");
		elementClassName = className.substring(startingIndex, className.length()).replace(".", "");
		elementName = Thread.currentThread().getStackTrace()[2].getMethodName();
		return parseLocator(inText);
	}

}
