package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DefaultUtilityFieldDecorator implements UtilityFieldDecorator {

	@Override
	public Object decorate(WebDriver driver, Field field) {
		Class<?> clazz = field.getType();
		
		if (Utility.class.isAssignableFrom(clazz)) {
			return instantiateUtility(driver, field);
		}
		
		return null;
	}
	
	protected Utility instantiateUtility(WebDriver driver, Field field) {
		try {
			return (Utility) field.getType().getConstructor(WebDriver.class)
					.newInstance(driver);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
