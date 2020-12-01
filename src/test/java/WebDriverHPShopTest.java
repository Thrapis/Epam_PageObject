import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobject_model.model.SearchAttribute;
import pageobject_model.page.HPShopCartPage;
import pageobject_model.page.HPShopHomePage;
import org.testng.Assert;

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
                .openPage()
                .searchForTerms(productName)
                .selectProductLink(productName)
                .addToCart()
                .openCart();
        Assert.assertTrue(cartPage.getCartTotalCost() > 0.0);
        Assert.assertTrue(cartPage.getProductsFromCart().get(0).getText().contains(productName));
        Assert.assertEquals(cartPage.getProductCountFromCart(productName), 1);
    }

    @Test
    public void verifyCartAfterPurgeTest() {
        driver.manage().deleteAllCookies();
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .openPage()
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
