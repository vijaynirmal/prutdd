package pageobjects;

import util.Interact;
import util.Locator;

public class Home {

	private Locator locator;

	public Home() {
		locator = new Locator();
	}

	// PAGE OBJECT IDENTIFIERS
	public Interact homeBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact insurancePlansBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact ForexBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact FinancialStmtBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact aboutBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public Interact contactUSBtn() {
		return new Interact(locator.getLocator(), locator.locatorData);
	}
	
	public void pause(Integer waitTime) {
		System.out.println("Pause for '" + waitTime + "' milliseconds.");
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
