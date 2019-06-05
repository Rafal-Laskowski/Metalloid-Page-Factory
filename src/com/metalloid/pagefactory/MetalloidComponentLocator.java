package com.metalloid.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class MetalloidComponentLocator implements ElementLocator {
	private final By by;
	private final SearchContext searchContext;

	public MetalloidComponentLocator(SearchContext searchContext, FindBy findBy) {
		this(searchContext, new MetalloidComponentAnnotations(findBy));
	}
	
	public MetalloidComponentLocator(SearchContext searchContext, AbstractAnnotations annotations) {
		this.searchContext = searchContext;
		this.by = annotations.buildBy();
	}

	@Override
	public WebElement findElement() {
		return searchContext.findElement(by);
	}

	@Override
	public List<WebElement> findElements() {
		return searchContext.findElements(by);
	}
	
	public By getLocator() {
		return this.by;
	}
}
