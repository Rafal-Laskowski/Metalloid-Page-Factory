package com.github.metalloid.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@PageFactoryFinder(FindBys.FindByBuilder.class)
public @interface FindBys {
    FindBy[] value();

    class FindByBuilder extends AbstractFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            FindBys findBys = (FindBys) annotation;
            FindBy[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];
            for(int i = 0; i < findByArray.length; ++i) {
                byArray[i] = FindByParser.parse(findByArray[i]);
            }

            return new ByChained(byArray);
        }
    }
}
