package com.github.metalloid.pagefactory.controls;

import org.openqa.selenium.*;

import java.util.List;

public class Control implements SearchContext {
	private final By locator;
	private final int index;
	protected final WebDriver driver;
	private final SearchContext searchContext;

	public Control(WebDriver driver, SearchContext searchContext, By locator) {
		this(driver, searchContext, locator, 0);
	}

	public Control(WebDriver driver, SearchContext searchContext, By locator, Integer index) {
		this.locator = locator;
		this.index = index;
		this.driver = driver;
		this.searchContext = searchContext;
	}

	public WebElement element() {
		try {
			return searchContext.findElements(locator).get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException(String.format("Cannot find element with selector: %s | Element searched in context of %s", locator.toString(), searchContext.toString()));
		}
	}

	public boolean exists() {
		return searchContext.findElements(locator).size() > 0;
	}

	public boolean isDisplayed() {
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

	@Override
	public List<WebElement> findElements(By by) {
		return searchContext.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return searchContext.findElement(by);
	}
}
