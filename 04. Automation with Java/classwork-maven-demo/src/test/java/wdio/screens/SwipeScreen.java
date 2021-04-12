package wdio.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class SwipeScreen extends MobileScreen {
    public SwipeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    @Step("Go to Swipe tab")
    public void navigateTo() {
        driver.findElement(MobileBy.AccessibilityId("Swipe")).click();
    }
}
