[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.metalloid-project/page-factory/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.metalloid-project/page-factory)

Maven dependency:
```
        <dependency>
            <groupId>com.github.metalloid-project</groupId>
            <artifactId>page-factory</artifactId>
            <version>1.0.0</version>
        </dependency>
```

Compiled with `Java 1.8`

Already contains dependency:
```
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>3.141.59</version>
		</dependency>
```

Metalloid is an extension of Selenium WebDriver's Page Factory solution. 

Introduces Selenium-based annotation like `@FindBy` but instead of using the `WebElement` interface, you can create a custom class which represents the element you found. For example: It can be a `Button` class or a `TextField` class. It can be anything, `Dropdown`, `Link`, `MyCustomControl`...

Another enhancement is annotation `@FindComponent` to insert custom elements which contains multiple other elements. Any grid control might be an example. The component is also build in Page Object style and can contain `@FindBy` annotation and more...

Go to `Wiki` section to read more about it.
