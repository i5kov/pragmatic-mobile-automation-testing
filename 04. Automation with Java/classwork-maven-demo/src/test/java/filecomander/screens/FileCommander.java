package filecomander.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.testng.Assert;

public class FileCommander extends MobileScreen {
    public FileCommander(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void skipWelcomeScreen() {
        findByText("Continue").click();
        Assert.assertTrue(findByText("Manage files", false).isDisplayed());

        findByText("Next").click();
        Assert.assertTrue(findByText("Clear the", false).isDisplayed());

        findByText("Next").click();
        Assert.assertTrue(findByText("Secure files", false).isDisplayed());

        findByText("Next").click();
        Assert.assertTrue(findByText("Listen to music", false).isDisplayed());

        findByText("Next").click();
        Assert.assertTrue(findByText("Convert files", false).isDisplayed());

        findByText("Next").click();
        Assert.assertTrue(findByText("FREE cloud", false).isDisplayed());

        findByText("Next").click();

        driver.findElement(MobileBy.id("com.mobisystems.fileman:id/close")).click();
    }

    public void allowPermissions() {
        driver.findElement(MobileBy.id("com.android.permissioncontroller:id/permission_allow_button")).click();
    }
}
