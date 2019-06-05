package com.metalloid.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;

public class MetalloidControlLocator extends DefaultElementLocator implements ElementLocator {
	private final By by;

	public MetalloidControlLocator(SearchContext searchContext, Field field) {
		this(searchContext, new Annotations(field));
	}

	public MetalloidControlLocator(SearchContext searchContext, Annotations annotations) {
		super(searchContext, annotations);
		this.by = annotations.buildBy();
	}
	
	public By getLocator() { 
		return by;
	}
}
