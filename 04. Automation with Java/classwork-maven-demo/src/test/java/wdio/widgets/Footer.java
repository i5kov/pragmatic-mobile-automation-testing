package wdio.widgets;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class Footer {
    private final AppiumDriver<MobileElement> driver;

    public Footer(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public void navigateTo(String tab) {
        By locator = MobileBy.AccessibilityId(tab);
        driver.findElement(locator).click();
    }
}
