package com.github.metalloid.pagefactory.conditions;

import com.github.metalloid.pagefactory.controls.Control;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MetalloidConditionDecorator implements FieldDecorator {
	private final WebDriver driver;
	private final FieldDecorator fieldDecorator;

	public MetalloidConditionDecorator(WebDriver driver, FieldDecorator fieldDecorator) {
		this.driver = driver;
		this.fieldDecorator = fieldDecorator;
	}
	
	@Override
	public Object decorate(ClassLoader loader, Field field) {
		Object object = fieldDecorator.decorate(loader, field);

		if (object != null) {
			WebElement element = null;
			if (field.getType().isAssignableFrom(WebElement.class)) {
				element = (WebElement) object;
			} else if (Control.class.isAssignableFrom(field.getType())) {
				element = ((Control) object).element();
			}

			if (element != null) {
				try {
					new WebElementConditionEvaluator(field, driver, element).evaluate();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		return object;
	}
}
