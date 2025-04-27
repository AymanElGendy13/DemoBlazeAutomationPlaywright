package core.ui;

import Utils.Logs;

public class Label extends Element {

    public Label(String selector) {
        super(selector);
    }

    public String getText() {
        Logs.info("Getting text from label: " + selector);
        return locator.textContent().trim(); // trim() removes whitespace
    }
}