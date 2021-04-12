package wdio.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.testng.Assert;

public class HomeScreen extends MobileScreen {
    public HomeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    @Step("Go to Swipe tab")
    public void navigateTo() {
        driver.findElement(MobileBy.AccessibilityId("Home")).click();
    }

    @Step("Verify Home screen loaded")
    public void verifyLoaded() {
        MobileElement element = findByText("Support");
        Assert.assertTrue(element.isDisplayed());
    }
}
