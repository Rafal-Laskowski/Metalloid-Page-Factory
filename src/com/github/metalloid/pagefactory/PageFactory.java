package com.github.metalloid.pagefactory;

import com.github.metalloid.pagefactory.components.Component;
import com.github.metalloid.pagefactory.components.FindComponent;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class PageFactory {

	public static void init(WebDriver driver, Object page) {
		init(driver, driver, page);
	}

	private static void init(WebDriver driver, SearchContext searchContext, Object page) {
		org.openqa.selenium.support.PageFactory
				.initElements(new MetalloidControlDecorator(driver, new MetalloidControlLocatorFactory(searchContext)), page);
		org.openqa.selenium.support.PageFactory
				.initElements(new WebElementFieldDecorator(new DefaultElementLocatorFactory(searchContext)), page);
		initComponents(driver, page);
	}

	private static void initComponents(WebDriver driver, Object page) {
		initElements(driver, driver, page);
	}

	private static void initElements(WebDriver driver, SearchContext searchContext, Object page) {
		for (Field field : page.getClass().getDeclaredFields()) {
			Annotation findComponentAnnotation = field.getAnnotation(FindComponent.class);
			if (findComponentAnnotation != null) {
				FindBy findBy = field.getType().getAnnotation(FindBy.class);

				MetalloidComponentLocatorFactory componentLocatorFactory = new MetalloidComponentLocatorFactory(
						searchContext, findBy);
				Object value = new MetalloidComponentDecorator(driver, componentLocatorFactory)
						.decorate(page.getClass().getClassLoader(), field);
				if (value != null) {
					finalizeField(page, field, value);

					Component instanceOfComponent = (Component) value;
					init(driver, instanceOfComponent, instanceOfComponent);
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
