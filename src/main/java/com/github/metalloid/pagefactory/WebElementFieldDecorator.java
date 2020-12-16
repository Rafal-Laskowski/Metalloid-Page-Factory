package com.github.metalloid.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class WebElementFieldDecorator extends DefaultFieldDecorator implements FieldDecorator {

	public WebElementFieldDecorator(ElementLocatorFactory factory) {
		super(factory);
	}
	
	protected boolean isDecoratableList(Field field) {
	    if (!List.class.isAssignableFrom(field.getType())) {
	      return false;
	    }

	    // Type erasure in Java isn't complete. Attempt to discover the generic
	    // type of the list.
	    Type genericType = field.getGenericType();
	    if (!(genericType instanceof ParameterizedType)) {
	      return false;
	    }

	    Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

	    if (!WebElement.class.equals(listType)) {
	      return false;
	    }

		return field.getAnnotation(FindBy.class) != null ||
				field.getAnnotation(FindAll.class) != null || field.getAnnotation(FindBys.class) != null;

	}

}
