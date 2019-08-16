package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;

public interface UtilityFieldDecorator {
	Object decorate(WebDriver driver, Field field);
}
