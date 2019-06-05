package com.metalloid.pagefactory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class MetalloidComponentLocatorFactory implements ElementLocatorFactory {
	private final SearchContext searchContext;
	private final FindBy findBy;

	public MetalloidComponentLocatorFactory(SearchContext searchContext, FindBy findBy) {
		this.searchContext = searchContext;
		this.findBy = findBy;
	}

	@Override
	public ElementLocator createLocator(Field field) {
		return new MetalloidComponentLocator(searchContext, findBy);
	}

}
