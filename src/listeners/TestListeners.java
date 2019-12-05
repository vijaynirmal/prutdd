package listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import driverutils.CapabilityFactory;
import driverutils.DriverManager;
import report.ExtentReportsManager;
import report.ExtentTestManager;

public class TestListeners implements ITestListener {

	private static String USER_DIR_PROP = "user.dir";
	// String filePath = ConfigProps.PATH_TO_SCREENSHOTS;
	String fileLoc = System.getProperty(USER_DIR_PROP) + "/screenshots/";
	String filePath = fileLoc.replace("/", File.separator);

	public static File outputFile;

	@Override
	public void onStart(ITestContext context) {
		System.out.println("========TEST STARTED========");
	}

	@Override
	public void onTestStart(ITestResult result) {
		try {

			CapabilityFactory.setMobileCapabilities();

		} catch (Exception e) {
			System.out.println("Failed to start driver.");
			e.printStackTrace();
		}

		System.out.println(result.getName() + " test case started");
		
		ExtentTestManager.startTest(result.getMethod().getMethodName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("***** Error " + result.getName() + " test has failed *****");
		String methodName = result.getName().toString().trim();
		System.out.println(methodName);

		try {
			ExtentTestManager.getTest().log(Status.FAIL, result.getInstanceName())
					.addScreenCaptureFromPath(takeScreenShot(methodName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExtentTestManager.getTest().log(Status.FAIL, result.getName().toUpperCase() + " " + result.getThrowable());
		
	}

	public String takeScreenShot(String methodName) {
		// get the driver
		WebDriver driver = DriverManager.getAppiumDriver();
		String destination = filePath + methodName + ".png";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in d drive with test method name
		try {
			FileUtils.copyFile(scrFile, new File(destination));
			System.out.println("***Placed screen shot in " + filePath + " ***");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return destination;
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("========TEST FINISHED========");
		ExtentTestManager.endTest();
		ExtentReportsManager.getInstance().flush();
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentTestManager.getTest().log(Status.PASS, result.getName() + " Test Passed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().log(Status.SKIP, result.getName().toUpperCase() + " Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

}
