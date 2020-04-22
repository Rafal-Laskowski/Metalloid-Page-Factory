package com.github.metalloid.pagefactory;

import com.github.metalloid.pagefactory.controls.Control;
import com.github.metalloid.utils.InstanceCreator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LocatingControlListHandler<T extends Control> implements InvocationHandler {
    private final WebDriver driver;
    private final SearchContext searchContext;
    private final By by;
    private Class<T> derivedClass;

    public LocatingControlListHandler(Class<T> derivedClass, WebDriver driver, SearchContext searchContext, By by) {
        this.derivedClass = derivedClass;
        this.driver = driver;
        this.searchContext = searchContext;
        this.by = by;
    }

    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        List<T> controls = new ArrayList<>();

        List<WebElement> elements = searchContext.findElements(by);
        for (int i = 0; i < elements.size(); i++) {
            controls.add(InstanceCreator.instanceOfControl(derivedClass, driver, searchContext, by, i));
        }

        try {
            return method.invoke(controls, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
