package pageobject_model.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HPShopHomePage extends HPShopPage{

    public HPShopHomePage(WebDriver driver) {
        super(driver);
    }

    public HPShopHomePage openPage() {
        driver.get(HOMEPAGE_URL);
        return this;
    }
}
