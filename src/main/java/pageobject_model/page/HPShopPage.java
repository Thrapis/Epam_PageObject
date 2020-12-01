package pageobject_model.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobject_model.model.SearchAttribute;
import pageobject_model.waits.WaitElementMethods;

public abstract class HPShopPage {

    protected WebDriver driver;

    protected static final String HOMEPAGE_URL = "https://hp-shop.by/";
    protected static final long WAIT_TIME_SECONDS = 10;

    public HPShopPage(WebDriver driver) {
        this.driver = driver;
    }

    public HPShopPage setSearchAttribute(SearchAttribute attribute) {
        WebElement attributeSelector = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//div[@id='product-categori']"), WAIT_TIME_SECONDS);
        attributeSelector.click();
        WebElement selection = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//label[text()='" + attribute.getAttribute() + "']/.."), WAIT_TIME_SECONDS);
        selection.click();
        return this;
    }

    public HPShopSearchResultsPage searchForTerms(String term){
        WebElement searchInputField = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//input[@name='search']"), WAIT_TIME_SECONDS);
        searchInputField.sendKeys(term);
        searchInputField.submit();
        return new HPShopSearchResultsPage(driver, term);
    }

    public HPShopCartPage openCart() {
        WebElement toCartButton = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//div[@class='header-cart-area']"), WAIT_TIME_SECONDS);
        toCartButton.click();
        return new HPShopCartPage(driver);
    }
}
