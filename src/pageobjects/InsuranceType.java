package pageobjects;

import util.Interact;
import util.Locator;

public class InsuranceType {

	private Locator locator;

	public InsuranceType() {
		locator = new Locator();
	}

	// PAGE OBJECT IDENTIFIERS
	public Interact motorCycleBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact recepientTxtBox() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact subjectTxtBox() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact msgTxtBox() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
}
