package com.github.metalloid.pagefactory.components;

import org.openqa.selenium.*;

import java.util.List;

public class Component implements SearchContext {
	private final WebDriver driver;
	private final By selector;
	
	protected Component(WebDriver driver, By selector) {
		this.driver = driver;
		this.selector = selector;
	}
	
	public boolean exists() {
		try {
			element();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public WebElement findElement(By by) {
		return element().findElement(by);
	}
	
	public List<WebElement> findElements(By by) {
		return element().findElements(by);
	}
	
	protected WebElement element() {
		return driver.findElement(selector);
	}
	
	public boolean isDisplayed() {
		return exists() && element().isDisplayed();
	}
}
