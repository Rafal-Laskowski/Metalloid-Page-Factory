package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Tab extends Utility {
	private final HashMap<Integer, String> tabs = new HashMap<>();
	private static int tabcount = 1;
	
	@Inject
	private JavaScript javaScript;
	
	@Inject
	private Wait wait;
	
	public Tab(WebDriver driver) {
		super(driver);
		tabs.put(1, driver.getWindowHandle());
		
		UtilsFactory.initUtilities(driver, this);
	}
	
	public void openNewTab() {
		javaScript.openNewTab();
		switchToTab(saveTab());
	}

	/**
	 * First tab has an index o 1. Second tab index of 2 etc...
	 * 
	 * @param tabNumberCountedSinceOne
	 */
	public void switchToTab(int tabNumberCountedSinceOne) {
		getWindowTabs();
		driver.switchTo().window(tabs.get(tabNumberCountedSinceOne));
	}

	public void switchToTab(String handle) {
		driver.switchTo().window(handle);
	}

	public void waitForNumberOfWindowToBe(int expectedNumberOfWindows) {
		wait.until(10, driver -> {
			try {
				wait.until(3, ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
				return true;
			} catch (TimeoutException e) {
				return null;
			}
		});
	}

	public void clickAndSwitchToNewTab(WebElement element) {
		element.click();
		waitForNumberOfWindowToBe(getCurrentNumberOfTabs()+1);
		switchToTab(getCurrentNumberOfTabs());
	}

	public void clickUsingJavaScriptAndSwitchToNewTab(WebElement element, int expectedNumberOfWindows,
			int tabNumberCountedSinceOne) {
		javaScript.click(element);
		waitForNumberOfWindowToBe(expectedNumberOfWindows);
		switchToTab(tabNumberCountedSinceOne);
	}

	public void waitForNewTabAndSwitchToIt(int expectedNumberOfWindow) {
		waitForNumberOfWindowToBe(getCurrentNumberOfTabs()+1);
		switchToTab(getCurrentNumberOfTabs());
	}

	public int getCurrentNumberOfTabs() {
		Set<String> all = driver.getWindowHandles();
		return all.size();
	}

	private int saveTab() {
		tabcount++;
		tabs.put(tabcount, getHandleForNewTab());
		return tabcount;
	}

	private String getHandleForNewTab() {
		Set<String> all = driver.getWindowHandles();
		for (String handle : all) {
			if (!tabs.values().contains(handle))
				return handle;
		}
		throw new RuntimeException("Tab calculation error");
	}

	private HashMap<Integer, String> getWindowTabs() {
		List<String> all = new ArrayList<>(driver.getWindowHandles());
		for (int i = 0; i < all.size(); i++) {
			tabs.put(i + 1, all.get(i));
		}
		return tabs;
	}
}
