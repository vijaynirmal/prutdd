package util;

import java.util.HashMap;

import org.openqa.selenium.By;

import driverutils.DriverManager;
import driverutils.MobileInteract;

public class Interact extends MobileInteract {
	public Interact(By locator, HashMap<String, String> locatorData) {
		super(locator, locatorData);
	}

	public Interact hideKeyboard() {
		if (TestRun.isAndroid()) {
			DriverManager.getAndroidDriver().hideKeyboard();
		} else {
			System.out.println("method not supported");
		}
		return this;
	}

}
