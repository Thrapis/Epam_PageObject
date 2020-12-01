import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobject_model.model.ProductInfo;
import pageobject_model.model.SearchAttribute;
import pageobject_model.page.HPShopCartPage;
import pageobject_model.page.HPShopHomePage;
import org.testng.Assert;

import java.util.List;

public class WebDriverHPShopTest {

    private WebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyCartAfterAdditionOfProductTest() {
        driver.manage().deleteAllCookies();
        String productName = "EliteDisplay S430c";
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .searchForTerms(productName)
                .selectProductLink(productName)
                .addToCart()
                .openCart();
        List<ProductInfo> products = cartPage.getProductsFromCart();
        Assert.assertEquals(cartPage.getCartTotalCost(), 3504.0);
        Assert.assertTrue(products.get(0).getName().contains(productName));
        Assert.assertEquals(products.get(0).getCount(), 1);
    }

    @Test
    public void verifyCartAfterPurgeTest() {
        driver.manage().deleteAllCookies();
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .setSearchAttribute(SearchAttribute.ACCESSORIES)
                .searchForTerms("HP")
                .addToCart("HP SMB Backpack Case")
                .addToCart("HP Odyssey Red/Black Backpack")
                .addToCart("Мышь HP X1500")
                .openCart()
                .purgeCart();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @AfterTest(alwaysRun = true)
    public void shutdown() {
        driver.quit();
        driver = null;
    }
}
