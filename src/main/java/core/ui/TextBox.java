package core.ui;

import Utils.Logs;
import com.microsoft.playwright.Locator;

public class TextBox extends Element {

    public TextBox(String selector) {
        super(selector);
    }

    public void sendData(String data) {
        Logs.info("Sending data to textbox: " + selector + " with data: " + data);
        locator.fill(data); // Auto-clears and types in one atomic operation
    }

    // Alternative for special cases (like slow typing simulation)
    public void typeData(String data) {
        Logs.info("Typing data to textbox: " + selector + " with data: " + data);
        locator.clear();
        locator.type(data); // Simulates keypresses with delays
    }

    // For direct value setting (no events triggered)
    public void setValue(String data) {
        Logs.info("Setting value to textbox: " + selector + " with data: " + data);
        locator.evaluate("(element, value) => element.value = value", data);
    }
}