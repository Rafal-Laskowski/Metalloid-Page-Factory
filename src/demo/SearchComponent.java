package demo;

import com.github.metalloid.pagefactory.FindBy;
import com.github.metalloid.pagefactory.PageFactory;
import com.github.metalloid.pagefactory.components.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@FindBy(css = "div.dRYYxd")
public class SearchComponent {

    @FindBy(name = "q")
    public TextField input;

    public SearchComponent(WebDriver driver) {
        PageFactory.init(driver, this);
    }

    public void setText(String string) {
        input.setText(string);
    }
}