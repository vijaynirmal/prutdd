package driverutils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import props.DeviceCategory;
import props.MobileOS;
import util.Config.ConfigProps;
import util.Config.StaticProps;
import util.TestRun;

@SuppressWarnings("unused")
public class DriverFactory {

	// DRIVERS
	private static ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<RemoteWebDriver>();
	private static ThreadLocal<AppiumDriver<MobileElement>> appiumDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	private static ThreadLocal<AndroidDriver<MobileElement>> androidDriver = new ThreadLocal<AndroidDriver<MobileElement>>();
	private static ThreadLocal<IOSDriver<MobileElement>> iOSDriver = new ThreadLocal<IOSDriver<MobileElement>>();

	private static AppiumDriverLocalService service;
	private static AppiumServiceBuilder builder;

	private static ThreadLocal<String> activeDeviceID = new ThreadLocal<String>();
	static ThreadLocal<String> activeMachineIP = new ThreadLocal<String>();
	private static ThreadLocal<String> activeDeviceProxyPort = new ThreadLocal<String>();
	private static ThreadLocal<Integer> localDebugProxyPort = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return null;
		}
	};

	public static String getHubAddress() {
		String host = util.Constants.LOCALHOST;
		String port = util.Constants.PORT;
		String hubUrl = "http://" + host + ":" + port + "/wd/hub";
		return hubUrl;
	}

	public static void initiateAppiumDriver(DeviceCategory deviceCategory, DesiredCapabilities capabilities)
			throws Exception {

		TestRun.setDeviceCategory(deviceCategory);
		initAppiumDrivers(getHubAddress(), capabilities);

	}

	public static void initAppiumDrivers(String hubUrl, DesiredCapabilities capabilities) throws Exception {
		if (TestRun.isAndroid()) {
			appiumDriver.set(new AndroidDriver<MobileElement>(new URL(hubUrl), capabilities));
		} else {
			appiumDriver.set(new IOSDriver<MobileElement>(new URL(hubUrl), capabilities));
		}
		DriverManager.setAppiumDriver(appiumDriver.get());
		if (TestRun.isAndroid()) {
			androidDriver.set((AndroidDriver<MobileElement>) appiumDriver.get());
			DriverManager.setAndroidDriver(androidDriver.get());
		} else {
			iOSDriver.set((IOSDriver<MobileElement>) appiumDriver.get());
			DriverManager.setIOSDriver(iOSDriver.get());
		}
	}

	public static void startAppiumServer() throws Exception {
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Starting Appium Server");
		try {
			runtime.exec(
					"cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			Thread.sleep(20000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void stopAppiumServer() {
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Stopping Appium Server");
		try {
			runtime.exec("taskkill /F /IM node.exe");
			runtime.exec("taskkill /F /IM cmd.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
