package pageobjects;

import util.Interact;
import util.Locator;

public class MotorCycleComprehensivePage {

	private Locator locator;
	
	public MotorCycleComprehensivePage() {
		locator = new Locator();
	}
	
	// PAGE OBJECT IDENTIFIERS
		public Interact sumInsuredTxtBox() {
			return new Interact(locator.getLocator(), locator.locatorData);
		}
		
		public Interact ccTxtBox() {
			return new Interact(locator.getLocator(), locator.locatorData);
		}
		
		public Interact ageofMCTxtBox() {
			return new Interact(locator.getLocator(), locator.locatorData);
		}
		
		public Interact calculateBtn() {
			return new Interact(locator.getLocator(), locator.locatorData);
		}
		
		
}
