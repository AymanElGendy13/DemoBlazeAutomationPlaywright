package core.ui;

import Utils.Logs;
import com.microsoft.playwright.Locator;
import java.util.List;
import java.util.stream.Collectors;

public class Button extends Label {

    public Button(String selector) {
        super(selector);
    }

    public void click() {
        Logs.info("Clicking on button: " + selector);
        locator.click(); // Auto-waits, auto-scrolls, and checks clickability
    }

    public void doubleClick() {
        Logs.info("Double-clicking on button: " + selector);
        locator.dblclick(); // Built-in double click
    }

    public List<Button> asList() {
        return locator.all() // Gets all matching elements
                .stream()
                .map(e -> new Button(selector))
                .collect(Collectors.toList());
    }
}