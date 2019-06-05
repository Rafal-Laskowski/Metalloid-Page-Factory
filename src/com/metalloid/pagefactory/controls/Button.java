package com.metalloid.pagefactory.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Button extends Control {
	
	public Button(WebDriver driver, By by) {
		super(driver, by);
	}
	
	public Button(WebDriver driver, By by, Integer integer) {
	    super(driver, by, integer);
	}
}
