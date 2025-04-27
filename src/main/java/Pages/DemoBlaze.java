package Pages;

import DriverSetup.DriverManager;
import com.microsoft.playwright.options.LoadState;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.qameta.allure.Step;
import com.microsoft.playwright.Locator;
import static Utils.PropertiesUtil.getPropertyValue;

public class DemoBlaze {
    // Page Objects
    private ProductPage productPage;
    private CartPage cartPage;

    // Navbar Locators
    private final Button navBarHome = new Button(".nav-item.active");
    private final Button navbarContact = new Button("a[data-target='#exampleModal']");
    private final Button navbarAboutUs = new Button("a[data-target='#videoModal']");
    private final Button navbarCart = new Button("#cartur");
    private final Button navbarLogin = new Button("#login2");
    private final Button navbarSignUp = new Button("#signin2");
    private final Button navbarMainLogoIcon = new Button("#nava");
    private final Button navBarLogout = new Button("#logout2");

    // Login Locators
    private final TextBox loginUsername = new TextBox("#loginusername");
    private final TextBox loginPassword = new TextBox("#loginpassword");
    private final Button loginBtn = new Button("button[onclick='logIn()']");
    private final Button loginCloseBtn = new Button("div[id='logInModal'] span[aria-hidden='true']");

    // SignUp Locators
    private final TextBox signupUsername = new TextBox("#sign-username");
    private final TextBox signupPassword = new TextBox("#sign-password");
    private final Button signupBtn = new Button("button[onclick='register()']");
    private final Button signupCloseBtn = new Button("div[id='signInModal'] span[aria-hidden='true']");

    // Contact Locators
    private final TextBox contactEmail = new TextBox("#recipient-email");
    private final TextBox contactName = new TextBox("#recipient-name");
    private final TextBox textMessage = new TextBox("#message-text");
    private final Button sendMessageBtn = new Button("button[onClick='send()']");
    private final Button contactCloseBtn = new Button("div[id='exampleModal'] span[aria-hidden='true']");

    // About Us Locators
    private final Button aboutUsCloseBtn = new Button("div[id='videoModal'] div[class='modal-header'] span[aria-hidden='true']");

    // Other Locators
    private final Label welcomeUser = new Label("#nameofuser");
    private final String prodCategory = "a.list-group-item[id='itemc']";
    private final String prodName = "h4.card-title";
    private final Label aboutusText = new Label("div[aria-label='Modal Window']");

    public ProductPage getProductPage() {
        if (productPage == null) {
            productPage = new ProductPage();
        }
        return productPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    @Step("Open DemoBlaze site")
    public void openSite() {
        DriverManager.navigateToUrl(getPropertyValue("baseURL"));
    }

    @Step("Navigate to navbar: {navbarName}")
    public DemoBlaze navigateToNavbar(String navbarName) {
        //refresh the page
        DriverManager.getPage().reload();
        switch (navbarName.toLowerCase()) {
            case "home" -> navBarHome.click();
            case "contact" -> navbarContact.click();
            case "about us" -> navbarAboutUs.click();
            case "cart" -> navbarCart.click();
            case "login" -> navbarLogin.click();
            case "signup" -> navbarSignUp.click();
            case "mainlogoicon" -> navbarMainLogoIcon.click();
            case "logout" -> navBarLogout.click();
            default -> throw new IllegalArgumentException("Navbar not found: " + navbarName);
        }
        return this;
    }

    @Step("Login with username: {username} and password: {password}")
    public DemoBlaze login(String username, String password) {
        loginUsername.sendData(username);
        loginPassword.sendData(password);
        loginBtn.click();
        return this;
    }

    public String returnUsername() {
        return welcomeUser.getText();
    }

    @Step("Signup with username: {username} and password: {password}")
    public DemoBlaze signUp(String username, String password) {
        signupUsername.sendData(username);
        signupPassword.sendData(password);
        signupBtn.click();
        DriverManager.getPage().onDialog(dialog -> dialog.accept());
        return this;
    }

    @Step("Contact Us with email: {email}, name: {name}, message: {message}")
    public void contactUs(String email, String name, String message) {
        contactEmail.sendData(email);
        contactName.sendData(name);
        textMessage.sendData(message);
        sendMessageBtn.click();
    }

    @Step("Select category: {categoryName}")
    public void selectCategory(String categoryName) {
        // Wait for categories to be visible
        DriverManager.getPage().waitForSelector(prodCategory);

        // Click the category using text selector (more reliable)
        try {
            DriverManager.getPage().click("text=" + categoryName);

            // Wait for products to update after category selection
            DriverManager.getPage().waitForLoadState(LoadState.NETWORKIDLE);
            DriverManager.getPage().waitForTimeout(1000);

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to select category: " + categoryName, e);
        }
    }

    @Step("Select product: {productName}")
    public void selectProduct(String productName) {
        // Wait for products to be visible
        DriverManager.getPage().waitForSelector(prodName);

        Locator products = DriverManager.getPage().locator(prodName);

        // Try to find and click the product
        for (int i = 0; i < products.count(); i++) {
            if (products.nth(i).textContent().trim().equalsIgnoreCase(productName)) {
                products.nth(i).click();
                return;
            }
        }

        throw new IllegalArgumentException("Product not found: " + productName);
    }

    public void chooseProduct(String categoryName, String productName) {
        selectCategory(categoryName);
        selectProduct(productName);
    }

    @Step("Closing: {buttonName}")
    public void closeButton(String buttonName) {
        switch (buttonName.toLowerCase()) {
            case "login" -> loginCloseBtn.click();
            case "signup" -> signupCloseBtn.click();
            case "contact" -> contactCloseBtn.click();
            case "about us" -> aboutUsCloseBtn.click();
            default -> throw new IllegalArgumentException("Button not found: " + buttonName);
        }
    }

    @Step("Get About Us text")
    public DemoBlaze getTextAboutUs() {
        System.out.println(aboutusText.getText());
        return this;
    }
}