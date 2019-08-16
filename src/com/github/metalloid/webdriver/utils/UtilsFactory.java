package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.WebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class UtilsFactory {
	
	public static void initUtilities(WebDriver driver, Object object) {
		initUtilities(driver, new DefaultUtilityFieldDecorator(), object);
	}
	
	public static void initUtilities(WebDriver driver, UtilityFieldDecorator decorator, Object object) {
		for (Field field : object.getClass().getDeclaredFields()) {
			Annotation utilityAnnotation = field.getAnnotation(Inject.class);
			if (utilityAnnotation != null) {
				Object util = decorator.decorate(driver, field);
				if (util != null) {
					finalizeField(object, field, util);
				}
			}
		}
	} 
	
	private static void finalizeField(Object page, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(page, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
