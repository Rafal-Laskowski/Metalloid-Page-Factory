package com.github.metalloid.utils;

import com.github.metalloid.pagefactory.controls.Control;
import com.github.metalloid.pagefactory.exceptions.InvalidImplementationException;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class InstanceCreator {

    public static <T extends Control> T instanceOfControl(Class<?> clazz, WebDriver driver, SearchContext searchContext, By by, Integer i) {
        try {
            return (T) Objects.requireNonNull(clazz).getConstructor(WebDriver.class, SearchContext.class, By.class, Integer.class)
                    .newInstance(driver, searchContext, by, i);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException f) {
            throw new InvalidImplementationException(
                    "\nTo create a list of Controls, you need to create a constructor like this:\n"
                            + "\npublic MyControlClass(WebDriver driver, SearchContext searchContext, By by, Integer integer) {\n"
                            + "    super(driver, searchContext, by, integer);\n" + "}");
        }
    }
}
