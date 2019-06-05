package demo;

import com.metalloid.pagefactory.FindAll;
import com.metalloid.pagefactory.FindBy;
import com.metalloid.pagefactory.PageFactory;
import com.metalloid.pagefactory.components.FindComponent;
import com.metalloid.pagefactory.conditions.ExpectedCondition;
import com.metalloid.pagefactory.conditions.Visibility;
import com.metalloid.pagefactory.controls.TextField;
import com.metalloid.webdriver.utils.*;
import demo.components.SearchComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GooglePage {
	
	@ExpectedCondition(timeout = 10, condition = Visibility.class)
	@FindBy(css = "input[name='q']")
	public TextField searchFieldTF;
	
	@FindBy(css = "input[name='q']")
	public List<TextField> searchFieldTFList;
	
	@FindBy(css = "input[name='q']")
	@ExpectedCondition(timeout = 10, condition = Visibility.class)
	public WebElement searchFieldWebElement;
	
	@FindBy(css = "input[name='q']")
	public List<WebElement> searchFieldWebElementList;
	
	@FindAll({@FindBy(css = "input[name='q']")})
	public TextField searchFieldAllTF;
	
	@FindAll({@FindBy(css = "input[name='q']")})
	public List<TextField> searchFieldAllTFList;
	
	@FindAll({@FindBy(css = "input[name='q']")})
	public WebElement searchFieldAllWebElement;
	
	@FindAll({@FindBy(css = "input[name='q']")})
	public List<WebElement> searchFieldAllWebElementList;
	
	@FindComponent
	public SearchComponent searchComponent;
	
	@Inject
	private Wait wait;
	
	@Inject
	private JavaScript js;
	
	@Inject
	private Mouse mouse;
	
	@Inject
	private Tab tab;
	
	public GooglePage(WebDriver driver) {
		PageFactory.init(driver, this);
		
		wait.until(d -> driver.getCurrentUrl().length() > 0);
		js.isEnabled(searchFieldWebElement);
		mouse.moveToWebElement(searchFieldWebElement).perform();
		
		tab.getCurrentNumberOfTabs();
	}
}
