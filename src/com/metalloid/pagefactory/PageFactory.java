package com.metalloid.pagefactory;

import com.metalloid.pagefactory.components.Component;
import com.metalloid.pagefactory.components.FindComponent;
import com.metalloid.pagefactory.conditions.MetalloidConditionDecorator;
import com.metalloid.webdriver.utils.UtilsFactory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class PageFactory {

	public static void init(WebDriver driver, Object page) {
		init(driver, driver, page);
		UtilsFactory.initUtilities(driver, page);
	}

	private static void init(WebDriver driver, SearchContext searchContext, Object page) {
		MetalloidControlLocatorFactory controlLocatorFactory = new MetalloidControlLocatorFactory(searchContext);
		org.openqa.selenium.support.PageFactory
				.initElements(new MetalloidConditionDecorator(driver, new MetalloidControlDecorator(driver, controlLocatorFactory)), page);
		org.openqa.selenium.support.PageFactory
				.initElements(new MetalloidConditionDecorator(driver, new WebElementFieldDecorator(new DefaultElementLocatorFactory(searchContext))), page);
		initComponents(driver, driver, page);
	}

	private static void initComponents(WebDriver driver, SearchContext searchContext, Object page) {
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
