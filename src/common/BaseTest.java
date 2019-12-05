package common;

import org.testng.annotations.BeforeMethod;
import util.TestRun;

public class BaseTest {

	protected String runParams;

	public void setRunParams(String runParams) {
		this.runParams = runParams;
	}

	@BeforeMethod(alwaysRun = true)
	public void initTest() throws Exception {
		TestRun.init(runParams);
	}
	
	public void closeAndroidKeyboard() {
		
	}
}
