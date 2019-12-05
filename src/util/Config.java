package util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.w3c.dom.Document;

public class Config {

	private static String SYSTEM_TEST_PROP = "system.test.";
	private static String USER_DIR_PROP = "user.dir";

	public interface ParamProps {
		String IOS = "iOS";
		String ANDROID = "Android";
		String ALL_DEVICES = StaticProps.ALL_DEVICES;
		String PHONE = StaticProps.PHONE;
		String TABLET = StaticProps.TABLET;
		
	}

	public interface GroupProps {
		String FULL = "Full";
		String SMOKE = "Smoke";
		String BROKEN = "Broken";
		String ON_REQUEST = "OnRequest";
		String DEBUG = "Debug";
		String SETTINGS = "Settings";
		String HOME_SCREEN = "HomeScreen";

	}

	public interface AttributeProps {
		String TEXT = "text";
		String NAME = "name";
		String VALUE = "value";
		String INDEX = "index";
		String RESOURCE_ID = "resource-id";
	}

	public interface StaticProps {
		String IOS = "iOS";
		String ANDROID = "Android";
		String ALL_DEVICES = "AllDevices";
		String PHONE = "Phone";
		String TABLET = "Tablet";
		Integer IMPLICIT_WAIT_MILLISECOND = 200;
		String LOCATOR_VARIABLE_PACKAGE = "$inPackage";
		String LOCATOR_VARIABLE = "$inText";
		String LOCATOR_VARIABLE_UPPER = "$inTextU";
		String LOCATOR_VARIABLE_LOWER = "$inTextL";
		String LOCATOR_ANDROID_UIAUTOMATOR = "AndroidUIAutomator";
		String LOCATOR_IOS_UIAUTOMATION = "IosUIAutomation";
		String LOCATOR_ACCESSIBILITY_ID = "AccessibilityId";
		String LOCATOR_XPATH = "XPath";
		String LOCATOR_NAME = "name";
		String LOCATOR_CLASS_NAME = "ClassName";
		String RESOURCE_ID = "resource-id";
		String LOCATOR_LINK_TEXT = "text";
		String LOCATOR_CSS = "CSS";
	}

	public interface ConfigProps {
		// RUNTIME CONFIG
		String APP_NAME = Config.getString("AppName");
		String MOBILE_OS = Config.getString("MobileOS");
		String DEVICE_CATEGORY = Config.getString("DeviceCategory");
		String DEVICE_NAME = Config.getString("DeviceName");
		String BROWSER_NAME = Config.getString("BrowserName");
		String PLATFORM_VERSION = Config.getString("PlatformVersion");
		String PLATFORM_NAME = Config.getString("PlatformName");
		Integer DEBUG_PROXY_PORT = Config.getInt("DebugProxyPort");
		String DEVICE_ID = Config.getString("DeviceID");
		Boolean RERUN_ON_FAILURE = Config.getBoolean("ReRunOnFailure");
		String STARTING_ORIENTATION = Config.getString("StartingOrientation");
		String AUTOMATION_NAME = Config.getString("AutomationName");

		// APP CONFIG
		String ELEMENT_FILE_PATH = Config.getFilePath("PathToElements");
		String APPDATA_FILE_PATH = Config.getFilePath("PathToAppDataConfig");
		String IOS_SCREENSHOT_WAIT = Config.getString("IOSAutoScreenshotWait");
		String IOS_AUTO_ACCEPT_ALERTS = Config.getString("AutoAcceptiOSAlerts");
		String APPLICATION_TITLE = Config.getString("ApplicationTitle");
		String PATH_TO_APP_PACKAGE = Config.getString("PathToAppPackage");
		String WDA_PATH = Config.getString("WDAPath");
		String BOOTSTRAP_PATH = Config.getString("BootStrapPath");

		// REPORT CONFIG
		Boolean ATTACH_APP_XMLTREE_LOGS = Config.getBoolean("AttachAppXMLTreeLogs");
		String MAX_TRY = Config.getString("Maxtry");
		String PATH_TO_SCREENSHOTS = Config.getString("PathToScreenshots");

		// WEBDRIVER CONFIG
		Integer MAX_WAIT_TIME = Config.getInt("WaitForWaitTime");
		String SERVER_COMMAND_TIMEOUT = Config.getString("ServerCommandTimeout");
		Integer POLLING_TIME = Config.getInt("PollingTime");
		Integer APP_LOADING_TIME = Config.getInt("AppLoadingTime");
	}

	public static String getFilePath(final String parameterName) {
		String parameterValue = System.getProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase());
		if (parameterValue != null) {
			return System.getProperty(USER_DIR_PROP) + parameterValue.replace("/", File.separator);
		}
		String propFromXML = getXPathValueFromFile(getConfigFileLocation(), getParameterValue(parameterName));
		System.setProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase(), propFromXML);
		return System.getProperty(USER_DIR_PROP) + propFromXML.replace("/", File.separator);
	}

	public static Integer getInt(final String parameterName) {
		return Integer.parseInt(getXmlProperty(parameterName));
	}

	public static Boolean getBoolean(final String parameterName) {
		return Boolean.valueOf(getXmlProperty(parameterName));
	}

	public static String getString(final String parameterName) {
		return getXmlProperty(parameterName);
	}

	private static String getXmlProperty(final String parameterName) {
		String propFromXML = getXPathValueFromFile(getConfigFileLocation(), getParameterValue(parameterName));
		System.setProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase(), propFromXML);
		return propFromXML;
	}

	// Set the TestNG file location

	private static String getConfigFileLocation() {
		String fileLoc = System.getProperty(USER_DIR_PROP) + "/resources/testng.xml";
		return fileLoc.replace("/", File.separator);
	}

	private static String getParameterValue(String parameterName) {
		return ("//parameter[@name='" + parameterName + "']/@value");
	}

	private static String getXPathValueFromFile(final String fileLocation, final String xpathQuery) {
		String value = null;
		try {
			File file = new File(fileLocation);
			DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = xmlFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.parse(file);
			XPathFactory xpathFact = XPathFactory.newInstance();
			XPath xpath = xpathFact.newXPath();
			XPathExpression expr = xpath.compile(xpathQuery);
			value = (String) expr.evaluate(xmlDoc, XPathConstants.STRING);
		} catch (Exception e) {
			Assert.fail("Failed to retrieve configuration value from Config File at '" + fileLocation
					+ "' with xpath query '" + xpathQuery + "'.", e);
		}
		return value;
	}

	public static Integer getMaxWaitTime() {
		String property = System.getProperty(SYSTEM_TEST_PROP + "waitforwaittime");
		if (property == null) {
			return 5; // seconds
		}
		return Integer.parseInt(property);
	}

	public static Integer getPollingTime() {
		String property = System.getProperty(SYSTEM_TEST_PROP + "pollingtime");
		if (property == null) {
			return 500; // milliseconds
		}
		return Integer.parseInt(property);
	}

}
