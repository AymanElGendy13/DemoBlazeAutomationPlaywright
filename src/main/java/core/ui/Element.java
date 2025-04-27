package core.ui;

import DriverSetup.DriverManager;
import Utils.Logs;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class Element {
    protected String selector;
    protected Locator locator;

    public Element(String selector) {
        this.selector = selector;
        this.locator = DriverManager.getPage().locator(selector);
        Logs.info("Created element with selector: " + selector);
    }

    protected Page getPage() {
        Logs.info("Getting page from DriverManager");
        return DriverManager.getPage();
    }

    protected Locator getLocator() {
        return locator;
    }

    public boolean isDisplayed() {
        try {
            return locator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSelector() {
        return selector;
    }
}