package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.function.Function;

public class Mouse extends Utility {
	private Actions actions;

	public Mouse(WebDriver driver) {
		super(driver);
		actions = new Actions(driver);
	}

	public Mouse moveToWebElement(WebElement element, int offsetX, int offsetY) {
		return addAction(a -> actions.moveToElement(element).moveByOffset(offsetX, offsetY));
	}

	public Mouse moveToWebElement(WebElement element) {
		return addAction(a -> a.moveToElement(element).moveByOffset(1, 0));
	}

	public Mouse moveToWebElement(By by) {
		return addAction(a -> a.moveToElement(driver.findElement(by)).moveByOffset(1, 0));
	}

	public Mouse moveToWebElement(WebElement reduceScopeToWebElement, By by) {
		return addAction(a -> a.moveToElement(reduceScopeToWebElement.findElement(by)).moveByOffset(1, 0));
	}

	public void perform() {
		actions.perform();

		actions = new Actions(driver);
	}

	public Mouse doubleClick() {
		return addAction(Actions::doubleClick);
	}

	public Mouse click() {
		return addAction(Actions::click);
	}

	public void dragAndDrop(WebElement draggedFrom, WebElement draggedTo) {
		actions.moveToElement(draggedFrom).pause(Duration.ofSeconds(1)).clickAndHold(draggedFrom)
				.pause(Duration.ofSeconds(1)).moveByOffset(1, 0).moveToElement(draggedTo).moveByOffset(1, 0)
				.pause(Duration.ofSeconds(1)).release().perform();
	}

	public Mouse clickAndHold() {
		return addAction(Actions::clickAndHold);
	}

	public Mouse release() {
		return addAction(Actions::release);
	}

	public Mouse moveByOffset(int xOffset, int yOffset) {
		return addAction(a -> a.moveByOffset(xOffset, yOffset));
	}

	private Mouse addAction(Function<Actions, Actions> action) {
		actions = action.apply(actions);
		return this;
	}
}
