package com.metalloid.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

public class MetalloidComponentAnnotations extends AbstractAnnotations {
	private final FindBy findBy;

	public MetalloidComponentAnnotations(FindBy findBy) {
		this.findBy = findBy;
	}

	@Override
	public By buildBy() {
		return FindByParser.parse(findBy);
	}

	@Override
	public boolean isLookupCached() {
		return false;
	}

}
