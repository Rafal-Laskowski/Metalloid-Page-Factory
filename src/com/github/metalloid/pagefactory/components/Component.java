package com.github.metalloid.pagefactory.components;

import com.github.metalloid.pagefactory.FindBy;
import com.github.metalloid.pagefactory.FindByParser;
import org.openqa.selenium.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Component implements SearchContext {
	private final WebDriver driver;
	private final By selector;

	/**
	 * DO NOT INVOKE MANUALLY!
	 *
	 * This constructor is called by Metalloid using Java Reflection API
	 * @param driver
	 * @param selector
	 */
	protected Component(WebDriver driver, By selector) {
		this.driver = driver;
		this.selector = selector;
	}

	/**
	 * Use this constructor only if you plan to create your own Component without using @FindComponent annotation.
	 * If you want to use @FindComponent annotation, use 2-argumented constructor.
	 * @param driver
	 */
	protected Component(WebDriver driver) {
		this.driver = driver;
		FindBy findBy = this.getClass().getAnnotation(FindBy.class);
		if (findBy != null) {
			this.selector = FindByParser.parse(findBy);
		} else {
			throw new IllegalArgumentException(new IllegalAccessException("You can't use this constructor without annotating your class with @FindBy annotation!"));
		}
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
