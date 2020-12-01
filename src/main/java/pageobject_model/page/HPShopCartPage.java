package pageobject_model.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import pageobject_model.model.ProductInfo;
import pageobject_model.waits.WaitElementMethods;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HPShopCartPage extends HPShopPage{

    private final static String productPathTemplate = "//a[contains(text(), '$')]/../../..//input[@class='shk-count']";

    public HPShopCartPage(WebDriver driver) {
        super(driver);
    }

    public List<ProductInfo> getProductsFromCart() {
        By productNamesLocator = By.xpath("//td[@class='th-details']/child::h2/child::a");
        By productPricesLocator = By.xpath("//td[@class='th-price']");
        By productCountsLocator = By.xpath("//td[@class='th-qty']//input[@name='count']");
        Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        List<WebElement> productNames = (List<WebElement>) wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productNamesLocator));
        List<WebElement> productPrices = (List<WebElement>) wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productPricesLocator));
        List<WebElement> productCounts = (List<WebElement>) wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productCountsLocator));
        List<ProductInfo> products = new ArrayList<ProductInfo>();
        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i).getText();
            int count = Integer.parseInt(productCounts.get(i).getAttribute("value"));
            double price = Double.parseDouble(productPrices.get(0).getText()
                    .substring(0, productPrices.get(0).getText().length() - 3)
                    .replace(" ", ""));
            products.add(new ProductInfo(name, count, price));
        }
        return products;
    }

    public int getProductCountFromCart(String name) {
        WebElement productCountElement = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath(productPathTemplate.replace("$", name)), WAIT_TIME_SECONDS);
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
       return emptyCartMessage.isDisplayed();
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
