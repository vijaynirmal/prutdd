package pageobjects;

import util.Interact;
import util.Locator;

public class InsurancePicker {

	private Locator locator;

	public InsurancePicker() {
		locator = new Locator();
	}

	// PAGE OBJECT IDENTIFIERS
	public Interact insurancePlansBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact premiumCalBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
}
