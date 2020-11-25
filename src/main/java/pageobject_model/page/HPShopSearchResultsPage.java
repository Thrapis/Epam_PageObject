package pageobject_model.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import pageobject_model.waits.WaitElementMethods;

public class HPShopSearchResultsPage extends HPShopPage{

    private String term;

    public HPShopSearchResultsPage(WebDriver driver, String term) {
        super(driver);
        this.term = term;
    }

    public HPShopProductPage selectProductLink(String name) {
        WebElement productInfoLink = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//a[contains(text(), '" + name + "')]"), WAIT_TIME_SECONDS);
        productInfoLink.click();
        return new HPShopProductPage(driver);
    }

    public HPShopSearchResultsPage addToCart(String name) {
        WebElement addToCartButton = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//a[contains(text(), '" + name + "')]/../..//button[@title='В корзину']"), WAIT_TIME_SECONDS);
        addToCartButton.click();
        return this;
    }
}
