package driverutils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class DriverManager {

	private static ThreadLocal<AppiumDriver<MobileElement>> appiumDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	private static ThreadLocal<AndroidDriver<MobileElement>> androidDriver = new ThreadLocal<AndroidDriver<MobileElement>>();
	private static ThreadLocal<IOSDriver<MobileElement>> iOSDriver = new ThreadLocal<IOSDriver<MobileElement>>();

	// Set AppiumDriver
	public static synchronized void setAppiumDriver(AppiumDriver<MobileElement> driver) {
		appiumDriver.set(driver);
	}

	// Get AppiumDriver
	public static AppiumDriver<MobileElement> getAppiumDriver() {
		return appiumDriver.get();
	}

	// Set IOSDriver
	public static synchronized void setIOSDriver(IOSDriver<MobileElement> driver) {
		iOSDriver.set(driver);
	}

	// Get IOSDriver
	public static IOSDriver<MobileElement> getIOSDriver() {
		return iOSDriver.get();
	}

	// Set AndroidDriver
	public static synchronized void setAndroidDriver(AndroidDriver<MobileElement> driver) {
		androidDriver.set(driver);
	}

	// Get AndroidDriver
	public static AndroidDriver<MobileElement> getAndroidDriver() {
		return androidDriver.get();
	}

	public static void stopAppiumDriver() {
		DriverManager.getAppiumDriver().quit();
	}

	public void stopAndroidDriver() {
		DriverManager.getAndroidDriver().quit();
	}

	public void closeAndroidKeyboard() {
		DriverManager.getAndroidDriver().navigate().back();
	}
}
