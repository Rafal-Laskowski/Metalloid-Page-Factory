package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

public class Google {
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "src/demo/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.google.pl/");

		try {
			GooglePage page = new GooglePage(driver);

			assertTrue(page.searchFieldTF.exists());
			assertTrue(page.searchFieldTFList.get(0).exists());
			assertTrue(page.searchFieldWebElement.isDisplayed());
			assertTrue(page.searchFieldWebElementList.get(0).isDisplayed());
			assertTrue(page.searchFieldAllTF.exists());
			assertTrue(page.searchFieldAllTFList.get(0).exists());
			assertTrue(page.searchFieldAllWebElement.isDisplayed());
			assertTrue(page.searchFieldAllWebElementList.get(0).isDisplayed());

			assertTrue(page.searchComponent.exists());
			assertTrue(page.searchComponent.input.exists());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}

}
