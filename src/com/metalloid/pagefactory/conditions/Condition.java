package com.metalloid.pagefactory.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public abstract class Condition {
	protected final WebDriver driver;
	protected final WebElement element;
	
	public Condition(WebDriver driver, WebElement element) {
		this.element = element;
		this.driver = driver;
	}

	public abstract ExpectedCondition<?> getExpectedCondition();
}
