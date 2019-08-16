package com.github.metalloid.webdriver.utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Wait extends Utility {
	private List<Class<? extends Throwable>> exceptionsToIgnore = new ArrayList<>();
	private Duration duration = Duration.ofMillis(500);
	private String exceptionInfo;

	public Wait(WebDriver driver) {
		super(driver);
	}
	
	public Wait ignoring(Class<? extends Throwable> exception) {
		exceptionsToIgnore.add(exception);
		return this;
	}
	
	public Wait pollingEvery(Duration duration) {
		this.duration = duration;
		return this;
	}

	public Wait setExceptionMessage(String message) {
		this.exceptionInfo = message;
		return this;
	}

	public <T> T until(boolean throwTimeout, int timeout, ExpectedCondition<T> condition) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			exceptionsToIgnore.forEach(wait::ignoring);
			wait.pollingEvery(duration);
			
			if (exceptionInfo != null)
				wait.withMessage(exceptionInfo);
			
			return wait.until(condition);
		} catch (TimeoutException e) {
			if (throwTimeout)
				throw e;
			else {
				return null;
			}
		} finally {
			exceptionsToIgnore = new ArrayList<>();
			exceptionsToIgnore.add(WebDriverException.class);
			duration = Duration.ofMillis(500);
			exceptionInfo = null;
		}
	}

	public <T> T until(int timeout, ExpectedCondition<T> condition) {
		return until(true, timeout, condition);
	}

	public <T> T until(boolean throwTimeout, ExpectedCondition<T> condition) {
		return until(throwTimeout, 10, condition);
	}

	public <T> T until(ExpectedCondition<T> condition) {
		return until(true, 10, condition);
	}

	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ignore) {
		}
	}
}
