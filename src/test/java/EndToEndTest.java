import DriverSetup.DriverManager;
import Pages.DemoBlaze;
import Utils.JsonUtils;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.*;
import Listeners.TestNGListeners;

import static Utils.PropertiesUtil.getPropertyValue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(TestNGListeners.class)
public class EndToEndTest {

    DemoBlaze demoBlaze;
    JsonUtils testDataOne, testDataTwo;

    @BeforeClass
    public void beforeClass() {
        testDataOne = new JsonUtils("e2eScenarioOne-test-data");
        testDataTwo = new JsonUtils("e2eScenarioTwo-test-data");
    }

    @BeforeMethod
    public void setUp() {
        DriverManager.createDriverInstance(getPropertyValue("browserType"));
        demoBlaze = new DemoBlaze();
        demoBlaze.openSite();
    }

    @Test(enabled = true, priority = 1)
    public void standardUserPurchaseFlow() {
        // Login
        demoBlaze.navigateToNavbar("login")
                .login(
                        testDataOne.getJsonData("login.username"),
                        testDataOne.getJsonData("login.password")
                );

        // Verify login (Playwright assertion)
        String welcomeText = demoBlaze.returnUsername();
        assertThat(DriverManager.getPage().locator("#nameofuser"))
                .hasText(testDataOne.getJsonData("assertions.welcomeText"));

        // First product
        demoBlaze.chooseProduct(
                testDataOne.getJsonData("chooseProduct[0].category"),
                testDataOne.getJsonData("chooseProduct[0].product")
        );
        demoBlaze.getProductPage().addToCart();

        // Verify product name
        String prodName = demoBlaze.getProductPage().getProductName();
        assertThat(DriverManager.getPage().locator(".name"))
                .hasText(testDataOne.getJsonData("assertions.productName"));

        demoBlaze.navigateToNavbar("mainlogoicon");

        // Second product
        demoBlaze.chooseProduct(
                testDataOne.getJsonData("chooseProduct[1].category"),
                testDataOne.getJsonData("chooseProduct[1].product")
        );
        demoBlaze.getProductPage().addToCart();

        // Checkout
        demoBlaze.navigateToNavbar("cart")
                .getCartPage()
                .placeOrder()
                .setTotalOrder(
                        testDataOne.getJsonData("order.name"),
                        testDataOne.getJsonData("order.country"),
                        testDataOne.getJsonData("order.city"),
                        testDataOne.getJsonData("order.creditCard"),
                        testDataOne.getJsonData("order.month"),
                        testDataOne.getJsonData("order.year")
                )
                .clickPurchaseOk();
    }

    @Test(enabled = true, priority = 2)
    public void registeredUserFullJourney() {
        demoBlaze.navigateToNavbar("signup")
                .signUp("asadsadsad", "123123")
                .closeButton("signup");

        // Login
        demoBlaze.navigateToNavbar("login")
                .login(
                        testDataTwo.getJsonData("login.username"),
                        testDataTwo.getJsonData("login.password")
                );

        // Verify login
        assertThat(DriverManager.getPage().locator("#nameofuser"))
                .hasText(testDataTwo.getJsonData("assertions.welcomeText"));

        // First product
        demoBlaze.chooseProduct(
                testDataTwo.getJsonData("chooseProduct[0].category"),
                testDataTwo.getJsonData("chooseProduct[0].product")
        );
        demoBlaze.getProductPage().addToCart();

        // Contact Us
        demoBlaze.navigateToNavbar("contact")
                .contactUs(
                        testDataTwo.getJsonData("contact.email"),
                        testDataTwo.getJsonData("contact.name"),
                        testDataTwo.getJsonData("contact.message")
                );

        demoBlaze.navigateToNavbar("home")
                .chooseProduct(
                        testDataTwo.getJsonData("chooseProduct[1].category"),
                        testDataTwo.getJsonData("chooseProduct[1].product")
                );
        demoBlaze.getProductPage().addToCart();

        // Verify product
        assertThat(DriverManager.getPage().locator(".name"))
                .hasText(testDataTwo.getJsonData("assertions.productName"));

        demoBlaze.navigateToNavbar("about us")
                .getTextAboutUs()
                .closeButton("about us");

        // Checkout
        demoBlaze.navigateToNavbar("cart")
                .getCartPage()
                .placeOrder()
                .setTotalOrder(
                        testDataTwo.getJsonData("order.name"),
                        testDataTwo.getJsonData("order.country"),
                        testDataTwo.getJsonData("order.city"),
                        testDataTwo.getJsonData("order.creditCard"),
                        testDataTwo.getJsonData("order.month"),
                        testDataTwo.getJsonData("order.year"))
                .clickPurchaseOk();

        demoBlaze.navigateToNavbar("logout");
    }

    @Test(enabled = true, priority = 3)
    public void cartManagement() {
        // Login
        demoBlaze.navigateToNavbar("login")
                .login(
                        testDataTwo.getJsonData("login.username"),
                        testDataTwo.getJsonData("login.password")
                );

        // Verify login
        assertThat(DriverManager.getPage().locator("#nameofuser"))
                .hasText(testDataTwo.getJsonData("assertions.welcomeText"));

        // Add products
        demoBlaze.chooseProduct(
                testDataTwo.getJsonData("chooseProduct[0].category"),
                testDataTwo.getJsonData("chooseProduct[0].product")
        );
        demoBlaze.getProductPage().addToCart().addToCart();

        demoBlaze.navigateToNavbar("home")
                .chooseProduct(
                        testDataTwo.getJsonData("chooseProduct[1].category"),
                        testDataTwo.getJsonData("chooseProduct[1].product")
                );
        demoBlaze.getProductPage().addToCart();

        // Delete product
        demoBlaze.navigateToNavbar("cart")
                .getCartPage()
                .deleteProduct("MacBook Pro");

        // Verify total price
        int price = demoBlaze.getCartPage().getTotalCartPrice();
        assertThat(DriverManager.getPage().locator("#totalp"))
                .hasText(String.valueOf(price));

        // Checkout
        demoBlaze.getCartPage()
                .placeOrder()
                .setTotalOrder(
                        testDataTwo.getJsonData("order.name"),
                        testDataTwo.getJsonData("order.country"),
                        testDataTwo.getJsonData("order.city"),
                        testDataTwo.getJsonData("order.creditCard"),
                        testDataTwo.getJsonData("order.month"),
                        testDataTwo.getJsonData("order.year"))
                .clickPurchaseOk();

        demoBlaze.navigateToNavbar("logout");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.tearDown();
    }
}