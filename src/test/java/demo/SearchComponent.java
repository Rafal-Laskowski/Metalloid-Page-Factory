package demo;

import com.github.metalloid.pagefactory.FindBy;
import com.github.metalloid.pagefactory.PageFactory;
import com.github.metalloid.pagefactory.components.Component;
import org.openqa.selenium.WebDriver;

import java.util.List;

@FindBy(css = "div[jscontroller='mvYTse']")
public class SearchComponent extends Component {

    @FindBy(name = "q")
    public TextField input;

    @FindBy(name = "q")
    private List<TextField> inputs;

    public SearchComponent(WebDriver driver) {
        super(driver);
        PageFactory.init(driver, this);
    }

    public void setText(String string) {
        input.setText(string);
    }
}
