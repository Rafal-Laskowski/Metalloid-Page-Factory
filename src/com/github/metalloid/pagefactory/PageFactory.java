package com.github.metalloid.pagefactory;

import com.github.metalloid.logging.Logger;
import com.github.metalloid.pagefactory.components.Component;
import com.github.metalloid.pagefactory.components.FindComponent;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class PageFactory {
	private static Logger logger = new Logger(PageFactory.class);

	public static void init(WebDriver driver, Object page) {
		init(driver, driver, page);
	}

	private static void init(WebDriver driver, SearchContext searchContext, Object page) {
		initializeControls(driver, searchContext, page);
		initializeWebElements(searchContext, page);
		initInjectableComponents(driver, searchContext, page);
		initNonInjectableComponents(driver, page);
	}

	private static void initNonInjectableComponents(WebDriver driver, Object page) {
		FindBy findBy = page.getClass().getAnnotation(FindBy.class);
		if (findBy != null && Component.class.isAssignableFrom(page.getClass())) {
			SearchContext searchContext = (Component) page;

			initializeControls(driver, searchContext, page);
			initializeWebElements(searchContext, page);
			initInjectableComponents(driver, searchContext, page);
		}
	}

	private static void initInjectableComponents(WebDriver driver, SearchContext searchContext, Object page) {
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

	private static void initializeControls(WebDriver driver, SearchContext searchContext, Object page) {
		logger.debug("Initializing Controls and List<Control> in class: %s", page.getClass().getSimpleName());
		org.openqa.selenium.support.PageFactory
				.initElements(new MetalloidControlDecorator(driver, new MetalloidControlLocatorFactory(searchContext)), page);
	}

	private static void initializeWebElements(SearchContext searchContext, Object page) {
		org.openqa.selenium.support.PageFactory
				.initElements(new WebElementFieldDecorator(new DefaultElementLocatorFactory(searchContext)), page);
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
