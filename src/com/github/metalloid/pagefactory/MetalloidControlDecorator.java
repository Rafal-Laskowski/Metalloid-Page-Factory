package com.github.metalloid.pagefactory;

import com.github.metalloid.logging.Logger;
import com.github.metalloid.pagefactory.controls.Control;
import com.github.metalloid.pagefactory.utils.InstanceCreator;
import com.github.metalloid.pagefactory.utils.ListUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MetalloidControlDecorator implements FieldDecorator {
	private Logger logger = new Logger(this);
	protected final MetalloidControlLocatorFactory factory;
	private final WebDriver driver;

	public MetalloidControlDecorator(WebDriver driver, MetalloidControlLocatorFactory factory) {
		this.driver = driver;
		this.factory = factory;
	}

	@Override
	public Object decorate(ClassLoader loader, Field field) {
		String fieldName = field.getName();

		if (!(Control.class.isAssignableFrom(field.getType()) || isDecorableList(field))) {
			logger.warning("Field: [%s] is neither a control or List<>", fieldName);
			return null;
		}

		MetalloidControlLocator locator = (MetalloidControlLocator) factory.createLocator(field);
		if (locator == null) {
			logger.warning("MetalloidControlLocator did not return the selector for field: [%s]", fieldName);
			return null;
		}

		if (Control.class.isAssignableFrom(field.getType())) {
			logger.debug("Field: [%s] is assignable from Control.class", fieldName);
			return instantiateSingleControl(driver, field, locator);
		} else if (List.class.isAssignableFrom(field.getType())) {
			logger.debug("Field: [%s] is assignable from List.class", fieldName);
			return instantiateListOfControls(driver, field, locator);
		} else {
			return null;
		}
	}

	protected boolean isDecorableList(Field field) {
		if (!List.class.isAssignableFrom(field.getType())) {
			return false;
		}

		Class<?> clazz = ListUtils.getListType(field);
		
		if (WebElement.class.equals(clazz)) {
			return false;
		}

		if (!Objects.requireNonNull(clazz).getSuperclass().isAssignableFrom(Control.class)) {
			return false;
		}

		return field.getAnnotation(FindBy.class) != null || field.getAnnotation(FindAll.class) != null;

	}

	protected Control instantiateSingleControl(WebDriver driver, Field field, MetalloidControlLocator locator) {
		logger.debug("Initializing Control.class for Field: [%s] with By: [%s]", field.getName(), locator.getLocator());
		try {
			return (Control) field.getType().getConstructor(WebDriver.class, SearchContext.class, By.class).newInstance(driver, locator.getSearchContext(), locator.getLocator());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	protected <T> List<T> instantiateListOfControls(WebDriver driver, Field field, MetalloidControlLocator locator) {
		Class<?> classToInstantiate = ListUtils.getListType(field);

		logger.debug("Initializing List<? extends Control> for field: [%s] with By: [%s]", field.getName(), locator.getLocator());

		List<T> controls = new ArrayList<>();

		List<WebElement> foundElements = locator.findElements();
		logger.debug("Found [%d] elements with By: [%s]", foundElements.size(), locator.getLocator());
		for (int i = 0; i < foundElements.size(); i++) {
			@SuppressWarnings(value = "unchecked")
			T t = (T) InstanceCreator.instanceOfControl(classToInstantiate, driver, locator.getSearchContext(), locator.getLocator(), i);
			controls.add(t);
		}
		return controls;
	}
}
