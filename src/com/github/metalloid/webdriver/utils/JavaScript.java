package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class JavaScript extends Utility {
	private final JavascriptExecutor jsExecutor;

	public JavaScript(WebDriver driver) {
		super(driver);
		jsExecutor = (JavascriptExecutor) driver;
	}

	public void scrollToElement(WebElement element) {
		executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void click(WebElement element) {
		executeScript("arguments[0].click();", element);
	}

	public void deleteCookies() {
		// js function
		jsExecutor.executeScript(
				"document.cookie.split(\";\").forEach(function(c) { document.cookie = c.replace(/^ +/, \"\").replace(/=.*/, \"=;expires=\" + new Date().toUTCString() + \";path=/\"); });");
		// jquery function
		jsExecutor.executeScript("for (var it in $.cookie()) $.removeCookie(it);");
		// if cookie persists, set expiration date
		jsExecutor.executeScript("document.cookie = \"username=; expires=\" + new Date().toGMTString(); ");
	}

	public String getValue(WebElement element) {
		return (String) executeScript("return arguments[0].value", element);
	}

	public void setTextContent(WebElement element, String textContent) {
		executeScript("arguments[0].textContent = 'arguments[1]'", element, textContent);
	}

	public String getWidth(WebElement element) {
		return (String) executeScript("return window.getComputedStyle(arguments[0]).width", element);
	}

	public String getHeight(WebElement element) {
		return (String) executeScript("return window.getComputedStyle(arguments[0]).height", element);
	}

	@SuppressWarnings("unchecked")
	public List<WebElement> getElementsInsideIFrame(String iframeID, String cssSelector) {
		driver.switchTo().defaultContent();
		return (List<WebElement>) executeScript(
				"return document.getElementById(arguments[0]).contentDocument.querySelectorAll(arguments[1]);",
				iframeID, cssSelector);
	}

	public boolean isChecked(String cssSelector) {
		return (boolean) executeScript("return document.querySelector(arguments[0]).checked", cssSelector);
	}

	public boolean isChecked(WebElement element) {
		return (boolean) executeScript("return arguments[0].checked", element);
	}

	public void scrollUp(WebElement div) {
		executeScript("arguments[0].scrollTo(0,0);", div);
	}

	public String getAttribute(WebElement element, String attribute) {
		return (String) executeScript("return arguments[0].getAttribute(\"arguments[1]\");", element, attribute);
	}

	public boolean isEnabled(WebElement element) {
		return !(boolean) executeScript("return arguments[0].disabled", element);
	}

	public void scrollDown(WebElement element) {
		executeScript("arguments[0].scrollTo(0,5000);", element);
	}

	public void highlightElement(WebElement element) {
		executeScript("arguments[0].style.backgroundColor = \"#FDFF47\";", element);
		executeScript("arguments[0].style.outline = '#f00 solid 2px';", element);
	}
	
	public Object executeScript(String script, Object... arguments) {
		return jsExecutor.executeScript(script, arguments);
	}
	
	/**
	 * This method should NOT be explicitly invoked. Use
	 * selenium.window.openNewTab(); instead
	 */
	public void openNewTab() {
		executeScript("window.open('about:blank','_blank');");
	}
}
