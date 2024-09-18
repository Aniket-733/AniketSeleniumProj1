package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.AccountPage;
import pageobjects.LandingPage;
import pageobjects.LoginPage;
import resources.Base;

public class Login extends Base {
	
	WebDriver driver;
	Logger log;

	@BeforeMethod
	public void openApplication() throws IOException {
		
		log = LogManager.getLogger(Login.class.getName());

		driver = initializeDriver();
		log.debug("Browser got launched");
		driver.get(prop.getProperty("url"));
		log.debug("Navigated to application URL");
	}

	

	@Test(dataProvider = "loginData")
	public void login(String email, String password, String expectedResult) throws Exception {

		LandingPage landingPage = new LandingPage(driver);
		landingPage.myAccountDropdown().click();
		log.debug("Clicked on My accoung dropdown");
		landingPage.login().click();
		log.debug("Clicked on login button");

		LoginPage loginPage = new LoginPage(driver);
		// loginPage.userName().sendKeys(prop.getProperty("username"));
		// loginPage.password().sendKeys(prop.getProperty("password"));
		loginPage.userName().sendKeys(email);
		log.debug("Username got entered");
		loginPage.password().sendKeys(password);
		log.debug("Password got entered");
		loginPage.loginButton().click();
		log.debug("Clicked on login button");

		AccountPage accountPage = new AccountPage(driver);

		String actualResult;
		try {
			if (accountPage.myAccountText().isDisplayed())
				log.debug("User got logged in");
				actualResult = "Successfull";
		} catch (Exception e) {
			
			log.debug("User didn't logged in");
			actualResult = "Failure";
		}
		if(actualResult.equals(actualResult)) {
			log.info("Login test got passed");
			
		}else {
			log.error("Login test got failed");
		}
			

	}

	@AfterMethod
	public void closure() {

		driver.close();
		log.debug("Browser got closed");
	}

	@DataProvider
	public Object[][] loginData() {

		Object[][] data = { { "aniket.kulkarni733@gmail.com", "Aniket@3di", "Successfull" },
				{ "dummy@gmail.com", "1234", "Failure" } };
		return data;
	}

}
