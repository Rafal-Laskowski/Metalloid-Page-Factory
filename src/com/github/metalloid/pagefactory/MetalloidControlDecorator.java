package com.github.metalloid.pagefactory;

import com.github.metalloid.pagefactory.controls.Control;
import com.github.metalloid.pagefactory.exceptions.InvalidImplementationException;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MetalloidControlDecorator implements FieldDecorator {
	protected final MetalloidControlLocatorFactory factory;
	private final WebDriver driver;

	public MetalloidControlDecorator(WebDriver driver, MetalloidControlLocatorFactory factory) {
		this.driver = driver;
		this.factory = factory;
	}

	@Override
	public Object decorate(ClassLoader loader, Field field) {
		if (!(Control.class.isAssignableFrom(field.getType()) || isDecorableList(field))) {
			return null;
		}

		MetalloidControlLocator locator = (MetalloidControlLocator) factory.createLocator(field);
		if (locator == null) {
			return null;
		}

		if (Control.class.isAssignableFrom(field.getType())) {
			return instantiateSingleControl(driver, locator.getSearchContext(), field, locator.getLocator());
		} else if (List.class.isAssignableFrom(field.getType())) {
			return instantiateListOfControls(driver, locator.getSearchContext(), field, locator);
		} else {
			return null;
		}
	}

	protected boolean isDecorableList(Field field) {
		if (!List.class.isAssignableFrom(field.getType())) {
			return false;
		}

		Class<?> clazz = getListType(field);
		
		if (WebElement.class.equals(clazz)) {
			return false;
		}

		if (!Objects.requireNonNull(clazz).getSuperclass().isAssignableFrom(Control.class)) {
			return false;
		}

		return field.getAnnotation(FindBy.class) != null || field.getAnnotation(FindAll.class) != null;

	}

	private Class<?> getListType(Field field) {
		// Type erasure in Java isn't complete. Attempt to discover the generic
		// type of the list.
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return null;
		}

		Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

		Class<?> clazz;
		try {
			clazz = Class.forName(listType.getTypeName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return clazz;
	}

	protected Control instantiateSingleControl(WebDriver driver, SearchContext searchContext, Field field, By by) {
		try {
			return (Control) field.getType().getConstructor(WebDriver.class, SearchContext.class, By.class).newInstance(driver, searchContext, by);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	protected <T> List<T> instantiateListOfControls(WebDriver driver, SearchContext searchContext, Field field, MetalloidControlLocator locator) {
		Class<?> classToInstantiate = getListType(field);

		List<T> controls = new ArrayList<>();
		for (int i = 0; i < locator.findElements().size(); i++) {

			try {
				@SuppressWarnings("unchecked")
				T t = (T) Objects.requireNonNull(classToInstantiate).getConstructor(WebDriver.class, SearchContext.class, By.class, Integer.class)
						.newInstance(driver, searchContext, locator.getLocator(), i);
				controls.add(t);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException f) {
				throw new InvalidImplementationException(
						"\nTo create a list of Controls, you need to create a constructor like this:\n"
								+ "\npublic MyControlClass(WebDriver driver, By by, Integer integer) {\n"
								+ "    super(driver, by, integer);\n" + "}");
			}
		}
		return controls;
	}
}
