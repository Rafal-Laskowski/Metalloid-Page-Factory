package com.github.metalloid.pagefactory.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextField extends Control {
	
	public TextField(WebDriver driver, By locatorValue) {
        super(driver, locatorValue);
    }
	
	public TextField(WebDriver driver, By locatorValue, Integer i) {
        super(driver, locatorValue, i);
    }

    public void setText(String value) {
    	this.element().sendKeys(value);
    }

    @Override
    public String getText() {
        return this.element().getAttribute("value");
    }
    
    public void clear() {
    	this.element().clear();
    }
}
