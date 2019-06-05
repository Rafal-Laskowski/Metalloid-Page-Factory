package com.metalloid.pagefactory;

import com.metalloid.pagefactory.components.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MetalloidComponentDecorator implements FieldDecorator {
	private final WebDriver driver;
	private final MetalloidComponentLocatorFactory factory;

	public MetalloidComponentDecorator(WebDriver driver, MetalloidComponentLocatorFactory factory) {
		this.driver = driver;
		this.factory = factory;
	}

	@Override
	public Object decorate(ClassLoader loader, Field field) {
		if (!(Component.class.isAssignableFrom(field.getType()))) {
			return null;
		}
		
		MetalloidComponentLocator locator = (MetalloidComponentLocator) factory.createLocator(field);
		if (locator == null) {
			return null;
		}

		if (Component.class.isAssignableFrom(field.getType())) {
			return instantiateComponent(driver, field, locator);
		} else {
			return null;
		}
	}

	protected Component instantiateComponent(WebDriver driver, Field field, MetalloidComponentLocator locator) {
		try {
			return (Component) field.getType().getConstructor(WebDriver.class, By.class)
					.newInstance(driver, locator.getLocator());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
