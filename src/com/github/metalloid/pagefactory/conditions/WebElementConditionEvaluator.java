package com.github.metalloid.pagefactory.conditions;

import com.github.metalloid.webdriver.utils.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class WebElementConditionEvaluator {
	private final Field field;
	private final WebElement element;
	private final WebDriver driver;
	
	public WebElementConditionEvaluator(Field field, WebDriver driver, WebElement element) {
		this.field = field;
		this.element = element;
		this.driver = driver;
	}

	public void evaluate() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ExpectedCondition annotation = field.getAnnotation(ExpectedCondition.class);
		if (annotation != null) {
			Class<? extends Condition> conditionClass = annotation.condition();
			
			Condition condition = conditionClass
					.getConstructor(WebDriver.class, WebElement.class)
					.newInstance(driver, element);
				
			Wait wait = new Wait(driver);
			wait.until(annotation.timeout(), condition.getExpectedCondition());
		}
	}
}
