package filecomander.tests;

import com.pragmatic.framework.base.MobileTest;
import filecomander.screens.FileCommander;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends MobileTest {
    @Test
    public void testLogin() {
        FileCommander fileCommander = new FileCommander(driver);
        fileCommander.skipWelcomeScreen();
        fileCommander.allowPermissions();

        // Open side drawer
        System.out.println(driver.getPageSource());
        driver.findElement(MobileBy.AccessibilityId("Navigate up")).click();

        // Verify it is free edition
        MobileElement headerTitle = driver.findElement(MobileBy.id("com.mobisystems.fileman:id/drawer_header_license_info"));
        Assert.assertEquals(headerTitle.getText(), "FREE EDITION");
    }
}
