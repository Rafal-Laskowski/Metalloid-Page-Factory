package com.metalloid.pagefactory.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Control {
	private final By locator;
	private final int index;
	protected final WebDriver driver;

	public Control(WebDriver driver, By locator) {
		this(driver, locator, 0);
	}

	public Control(WebDriver driver, By locator, Integer index) {
		this.locator = locator;
		this.index = index;
		this.driver = driver;
	}

	public WebElement element() {
		return driver.findElements(locator).get(index);
	}

	public boolean exists() {
		return driver.findElements(locator).size() > 0;
	}

	public boolean visible() {
		if (exists())
			return element().isDisplayed();
		return false;
	}

	public void click() {
		this.element().click();
	}

	public String getText() {
		return this.element().getText();
	}
}
