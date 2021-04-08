package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Set;

public class SwipeScreen extends BaseScreen {
    public SwipeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.findElement(MobileBy.AccessibilityId("Swipe")).click();
    }
}