package util;

import props.DeviceCategory;
import props.MobileOS;
import util.Config.ConfigProps;

public class TestRun {

	private static ThreadLocal<MobileOS> mobileOS = new ThreadLocal<MobileOS>();
	private static ThreadLocal<DeviceCategory> deviceCategory = new ThreadLocal<DeviceCategory>();
	private static ThreadLocal<String> automationName = new ThreadLocal<String>();
	private static ThreadLocal<Boolean> mobile = new ThreadLocal<Boolean>();

	public static synchronized void init(String runParams) {

		setMobileOS(MobileOS.valueOf(ConfigProps.MOBILE_OS.toUpperCase()));
		setDeviceCategory(DeviceCategory.valueOf(ConfigProps.DEVICE_CATEGORY.toUpperCase()));

	}

	public static synchronized void setMobileOS(MobileOS os) {
		mobileOS.set(os);
	}

	public static synchronized MobileOS getMobileOS() {
		return mobileOS.get();
	}

	public static synchronized void setDeviceCategory(DeviceCategory devicecat) {
		deviceCategory.set(devicecat);
	}

	public static synchronized DeviceCategory getDeviceCategory() {
		return deviceCategory.get();
	}

	public static synchronized Boolean isIos() {
		return getMobileOS().equals(MobileOS.IOS);
	}

	public static synchronized Boolean isAndroid() {
		return getMobileOS().equals(MobileOS.ANDROID);
	}

	public static synchronized Boolean isPhone() {
		return getDeviceCategory().equals(DeviceCategory.PHONE);
	}

	public static synchronized Boolean isTablet() {
		return getDeviceCategory().equals(DeviceCategory.TABLET);
	}

	public static synchronized void setMobile(Boolean isMobile) {
		TestRun.mobile.set(isMobile);
	}

	public static synchronized Boolean isMobile() {
		return mobile.get();
	}

	public static synchronized void setAutomationName(String autoname) {
		automationName.set(autoname);
	}

	public static synchronized String getAutomationName() {
		return automationName.get();
	}

}
