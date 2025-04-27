package Pages;

import DriverSetup.DriverManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends DemoBlaze {
    // Locators
    private final Label totalPRICE = new Label("#totalp");
    private final Button placeOrderBtn = new Button(".btn.btn-success");
    private final TextBox placeOrderName = new TextBox("#name");
    private final TextBox placeOrderCountry = new TextBox("#country");
    private final TextBox placeOrderCity = new TextBox("#city");
    private final TextBox placeOrderCreditCard = new TextBox("#card");
    private final TextBox placeOrderMonth = new TextBox("#month");
    private final TextBox placeOrderYear = new TextBox("#year");
    private final Button placeOrderPurchaseBtn = new Button("button[onclick='purchaseOrder()']");
    private final Label purchaseMessage = new Label("//h2[normalize-space()='Thank you for your purchase!']");
    private final Label purchaseDetails = new Label(".lead.text-muted");
    private final Button purchaseOkBtn = new Button(".confirm.btn.btn-lg.btn-primary");
    private final String logoPic = ".sa-line.sa-long.animateSuccessLong";
    private final String productRows = "tbody tr.success";

    public CartPage() {
        super();
    }


    @Step("Placing order")
    public CartPage placeOrder() {
        placeOrderBtn.click();
        return this;
    }

    @Step("Setting total order with name: {name}, country: {country}, city: {city}, creditCard: {creditCard}, month: {month}, year: {year}")
    public CartPage setTotalOrder(String name, String country, String city,
                                  String creditCard, String month, String year) {
        placeOrderName.sendData(name);
        placeOrderCountry.sendData(country);
        placeOrderCity.sendData(city);
        placeOrderCreditCard.sendData(creditCard);
        placeOrderMonth.sendData(month);
        placeOrderYear.sendData(year);
        placeOrderPurchaseBtn.click();
        return this;
    }

    public String getPurchaseMessage() {
        return purchaseMessage.getText();
    }

    public Map<String, String> getPurchaseDetails() {
        Map<String, String> details = new HashMap<>();
        String fullText = purchaseDetails.getText();

        fullText.lines()
                .filter(line -> !line.trim().isEmpty())
                .forEach(line -> {
                    if (line.contains(":")) {
                        String[] parts = line.split(":", 2);
                        details.put(parts[0].trim(), parts[1].trim());
                    } else if (line.equals("Thank you for your purchase!")) {
                        details.put("header", line);
                    }
                });
        return details;
    }

    @Step("Clicking purchase OK button")
    public void clickPurchaseOk() {
        DriverManager.getPage().waitForSelector(logoPic);
        purchaseOkBtn.click();
    }

    @Step("Deleting product: {productName}")
    public void deleteProduct(String productName) {
        // Wait for cart items to load
        DriverManager.getPage().waitForSelector(productRows);

        Locator rows = DriverManager.getPage().locator(productRows);
        boolean productFound = false;

        for (int i = 0; i < rows.count(); i++) {
            String itemName = rows.nth(i).locator("td:nth-child(2)").textContent().trim();
            if (itemName.equals(productName)) {
                // Click delete and wait for removal
                rows.nth(i).locator("td a").click();

                // Wait for the row to disappear
                DriverManager.getPage().waitForSelector(productRows,
                        new Page.WaitForSelectorOptions()
                                .setState(WaitForSelectorState.HIDDEN)
                                .setTimeout(5000));

                productFound = true;
                break;
            }
        }

        if (!productFound) {
            throw new IllegalArgumentException("Product not found in cart: " + productName);
        }

        // Small delay to ensure UI updates
        DriverManager.getPage().waitForTimeout(1000);
    }

    public int getTotalCartPrice() {
        // Wait for total price to update
        DriverManager.getPage().waitForSelector("#totalp");

        String totalText = DriverManager.getPage().locator("#totalp").textContent().trim();
        if (totalText.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(totalText);
    }

}