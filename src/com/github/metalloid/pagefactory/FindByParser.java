package com.github.metalloid.pagefactory;

import org.openqa.selenium.By;

public class FindByParser {
	private static final String EMPTY = "";
	
	public static By parse(FindBy findBy) {
		if (!findBy.id().equals(EMPTY))
			return By.id(findBy.id());
		else if (!findBy.css().equals(EMPTY))
			return By.cssSelector(findBy.css());
		else if (!findBy.xpath().equals(EMPTY))
			return By.xpath(findBy.xpath());
		else if (!findBy.name().equals(EMPTY))
			return By.name(findBy.name());
		else if (!findBy.tagName().equals(EMPTY))
			return By.tagName(findBy.tagName());
		else if (!findBy.linkText().equals(EMPTY))
			return By.linkText(findBy.linkText());
		else if (!findBy.partialLinkText().equals(EMPTY))
			return By.partialLinkText(findBy.partialLinkText());
		else if (!findBy.className().equals(EMPTY))
			return By.className(findBy.className());
		else if (!findBy.text().equals(EMPTY))
			return By.xpath(String.format("//*[contains(text(), '%s')]", findBy.text()));
		else if (!findBy.textContains().equals(EMPTY))
			return By.xpath(String.format("//*[contains(text(), '%s')]", findBy.textContains()));
		else if (!findBy.textEquals().equals(EMPTY))
			return By.xpath(String.format("//*[text() = '%s')]", findBy.textEquals()));
		else if (!findBy.style().equals(EMPTY))
			return By.cssSelector(String.format("*[style='%s']", findBy.style()));

		throw new RuntimeException(String.format("FindBy: [%s] is not implemented", findBy.toString()));
	}
}
