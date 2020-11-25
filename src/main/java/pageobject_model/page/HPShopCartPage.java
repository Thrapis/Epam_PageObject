package pageobject_model.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobject_model.waits.WaitElementMethods;
import java.util.List;

public class HPShopCartPage extends HPShopPage{

    public HPShopCartPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getProductsFromCart() {
        List<WebElement> products = WaitElementMethods.waitForElementsLocatedBy(driver,
                By.xpath("//td[@class='th-details']/child::h2/child::a"), WAIT_TIME_SECONDS);
        return products;
    }

    public int getProductCountFromCart(String name) {
        WebElement productCountElement = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//a[contains(text(), '" + name + "')]/../../..//input[@class='shk-count']"), WAIT_TIME_SECONDS);
        return Integer.parseInt(productCountElement.getAttribute("value"));
    }

    public double getCartTotalCost() {
        WebElement cartTotal = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//td[@id='cart_total']"), WAIT_TIME_SECONDS);
        String cartTotalText = cartTotal.getText();
        double totalCost = Double.parseDouble(cartTotalText
                .substring(0, cartTotalText.length() - 3)
                .replace(" ", ""));
        return totalCost;
    }

    public boolean cartIsEmpty() {
       WebElement emptyCartMessage = WaitElementMethods.waitForElementLocatedBy(driver,
               By.xpath("//div[@id='shopCart']/p[text()='Ваша корзина пуста']"), WAIT_TIME_SECONDS);
       if (emptyCartMessage.isDisplayed())
           return true;
       return false;
    }

    public HPShopCartPage purgeCart() {
        WebElement clearCartButton = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//a[@id='butEmptyCart']"), WAIT_TIME_SECONDS);
        clearCartButton.click();
        WebElement confirmButton = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//div[@id='stuffHelper']//button[@id='confirmButton']"), WAIT_TIME_SECONDS);
        confirmButton.click();
        return this;
    }
}
