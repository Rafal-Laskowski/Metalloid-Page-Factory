package com.github.metalloid.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@PageFactoryFinder(FindBy.FindByBuilder.class)
public @interface FindBy {

	String id() default "";

	String name() default "";

	String className() default "";

	String css() default "";

	String tagName() default "";

	String linkText() default "";

	String partialLinkText() default "";

	String xpath() default "";

	String text() default "";

	String style() default "";

	class FindByBuilder extends AbstractFindByBuilder {
		public By buildIt(Object annotation, Field field) {
			FindBy findBy = (FindBy) annotation;

			return FindByParser.parse(findBy);
		}
	}
}
