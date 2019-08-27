package com.github.metalloid.pagefactory;

import org.openqa.selenium.By;

public class FindByParser {
	
	public static By parse(FindBy findBy) {
		if (!findBy.id().equals(""))
			return By.id(findBy.id());
		else if (!findBy.css().equals(""))
			return By.cssSelector(findBy.css());
		else if (!findBy.xpath().equals(""))
			return By.xpath(findBy.xpath());
		else if (!findBy.name().equals(""))
			return By.name(findBy.name());
		else if (!findBy.tagName().equals(""))
			return By.tagName(findBy.tagName());
		else if (!findBy.linkText().equals(""))
			return By.linkText(findBy.linkText());
		else if (!findBy.partialLinkText().equals(""))
			return By.partialLinkText(findBy.partialLinkText());
		else if (!findBy.className().equals(""))
			return By.className(findBy.className());
		else if (!findBy.text().equals(""))
			return By.xpath(String.format("//*[contains(text(), '%s')]", findBy.text()));
		else if (!findBy.style().equals(""))
			return By.cssSelector(String.format("*[style='%s']", findBy.style()));

		throw new RuntimeException("Not implemented");
	}
}
