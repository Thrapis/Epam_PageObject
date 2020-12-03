import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
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

    @BeforeMethod(alwaysRun = true)
    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    @Test
    public void verifyCartAfterAdditionOfProductTest() {
        String productName = "EliteDisplay S430c";
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .searchForTerms(productName)
                .selectProductLink(productName)
                .addToCart()
                .openCart();
        List<ProductInfo> products = cartPage.getProductsFromCart();
        Assert.assertEquals(cartPage.getCartTotalCost(), 3504.0);
        Assertions.assertThat(products.get(0).getName()).contains(productName);
        Assert.assertEquals(products.get(0).getCount(), 1);
    }

    @Test
    public void verifyCartAfterPurgeTest() {
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
