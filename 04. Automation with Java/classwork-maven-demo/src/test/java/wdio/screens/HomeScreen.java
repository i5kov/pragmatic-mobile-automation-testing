package wdio.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.testng.Assert;
import wdio.widgets.Footer;

public class HomeScreen extends MobileScreen {
    public Footer footer;

    public HomeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
        footer = new Footer(driver);
    }

    @Step("Verify Home screen loaded")
    public void verifyLoaded() {
        MobileElement element = findByText("Demo app", false);
        Assert.assertTrue(element.isDisplayed());
    }
}
