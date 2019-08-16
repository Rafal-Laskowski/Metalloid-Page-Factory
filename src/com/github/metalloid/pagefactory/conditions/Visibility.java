package com.github.metalloid.pagefactory.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Visibility extends Condition {

	public Visibility(WebDriver driver, WebElement element) {
		super(driver, element);
	}

	@Override
	public ExpectedCondition<?> getExpectedCondition() {
		return ExpectedConditions.visibilityOf(element);
	}
}
