package Pages;

import DriverSetup.DriverManager;
import com.microsoft.playwright.Page;
import core.ui.Button;
import core.ui.Label;
import io.qameta.allure.Step;

public class ProductPage extends DemoBlaze {
    // Locators
    private final Label productName = new Label(".name");
    private final Label productPrice = new Label(".price-container"); // Updated to match demo blaze
    private final Label productDescription = new Label("div[id='more-information'] p");
    private final Button addToCartButton = new Button(".btn-success.btn-lg");

    public ProductPage() {
        super();
    }

    @Step("Adding product to cart")
    public ProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText().replace("$", "").trim();
    }

    public String getProductDescription() {
        return productDescription.getText();
    }

}