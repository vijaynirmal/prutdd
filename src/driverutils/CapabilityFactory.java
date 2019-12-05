package driverutils;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;
import util.Config.ConfigProps;
import util.TestRun;

public class CapabilityFactory {

	@SuppressWarnings({ "deprecation" })
	public static void setMobileCapabilities() throws Exception {
		
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// set desired capabilities
		
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigProps.DEVICE_NAME);
		capabilities.setCapability(MobileCapabilityType.PLATFORM, ConfigProps.PLATFORM_NAME);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigProps.PLATFORM_VERSION);
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, ConfigProps.BROWSER_NAME);
		capabilities.setCapability(MobileCapabilityType.APP, ConfigProps.PATH_TO_APP_PACKAGE);
		capabilities.setCapability(MobileCapabilityType.ORIENTATION,ScreenOrientation.PORTRAIT);
		capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
		capabilities.setCapability("newCommandTimeout", 60 * 3);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,ConfigProps.AUTOMATION_NAME);
		
		if(TestRun.isIos()) {
			capabilities.setCapability("agentPath",ConfigProps.WDA_PATH);
			capabilities.setCapability("bootstrapPath",ConfigProps.BOOTSTRAP_PATH);
			capabilities.setCapability(MobileCapabilityType.UDID, ConfigProps.DEVICE_ID);
		} else {
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Appium");
		}
		
		

		DriverFactory.initiateAppiumDriver(TestRun.getDeviceCategory(), capabilities);

	}

}
