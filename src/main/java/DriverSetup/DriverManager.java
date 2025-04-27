package DriverSetup;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;

import java.util.List;

public class DriverManager {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    public static void createDriverInstance(String browserName) {
        playwright = Playwright.create();
        browser = switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setArgs(List.of("--start-maximized")));
            case "edge" -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setChannel("msedge").setHeadless(false).setArgs(List.of("--start-maximized")));
            default -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setChannel("chrome").setHeadless(false).setArgs(List.of("--start-maximized")));
        };
        page = browser.newContext(new Browser.NewContextOptions().setViewportSize(null)).newPage();
    }

    public static Page getPage() {
        if (page == null) throw new IllegalStateException("Page not initialized. Call createDriver() first.");
        return page;
    }

    public static void navigateToUrl(String url) {
        getPage().navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.LOAD));
    }

    public static void tearDown() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}