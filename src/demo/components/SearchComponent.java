package demo.components;

import com.metalloid.pagefactory.FindBy;
import com.metalloid.pagefactory.components.Component;
import com.metalloid.pagefactory.controls.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The component acts as a Page Object and is auto-initialized by Component class.
 * No need to use PageFactory.init
 * @author rafal.laskowski
 *
 */
@FindBy(css = "div[jscontroller='mvYTse']")
public class SearchComponent extends Component {
	
	@FindBy(name = "q")
	public TextField input;

	public SearchComponent(WebDriver driver, By by) {
		super(driver, by);
	}
	
	public void setText(String string) {
		input.setText(string);
	}
}
