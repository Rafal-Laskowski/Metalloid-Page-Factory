package com.metalloid.pagefactory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class MetalloidControlLocatorFactory implements ElementLocatorFactory {
	private final SearchContext searchContext;

	public MetalloidControlLocatorFactory(SearchContext searchContext) {
		this.searchContext = searchContext;
	}

	@Override
	public ElementLocator createLocator(Field field) {
		return new MetalloidControlLocator(searchContext, field);
	}
	
	public SearchContext getSearchContext() {
		return searchContext;
	}
}
