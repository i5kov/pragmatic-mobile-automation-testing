package com.pragmatic.framework.base;

import com.pragmatic.framework.enums.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

@SuppressWarnings("rawtypes")
public class MobileScreen {
    protected AppiumDriver<MobileElement> driver;

    public MobileScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public MobileElement findByText(String text) {
        return findByText(text, true);
    }

    public MobileElement findByText(String text, boolean exactMatch) {
        By locator;
        String automation = driver.getCapabilities().getCapability("automationName").toString();
        if (automation.equalsIgnoreCase(AutomationName.ANDROID_UIAUTOMATOR2)) {
            if (exactMatch) {
                locator = MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")");
            } else {
                locator = MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
            }
        } else if (automation.equalsIgnoreCase(AutomationName.IOS_XCUI_TEST)) {
            if (exactMatch) {
                String exactPredicate = "name == \"" + text + "\" OR label == \"" + text + "\"";
                locator = MobileBy.iOSNsPredicateString(exactPredicate);
            } else {
                String containsPredicate = "name contains '" + text + "' OR label contains '" + text + "'";
                locator = MobileBy.iOSNsPredicateString(containsPredicate);
            }
        } else {
            throw new RuntimeException("Unsupported automation technology: " + automation);
        }
        return (MobileElement) driver.findElement(locator);
    }

    @Step("Verify '{text}' text is visible")
    public void verifyTextVisible(String text) {
        Assert.assertTrue(findByText(text).isDisplayed());
    }

    public void swipe(Direction direction) {
        swipe(direction, 20, 500);
    }

    public void swipe(Direction direction, int offsetPercent) {
        swipe(direction, offsetPercent, 500);
    }

    @Step("Swipe '{direction}' with {offsetPercent} offset and {duration} duration")
    public void swipe(Direction direction, int offsetPercent, int duration) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int xStart = size.getWidth() / 2;
        int yStart = size.getHeight() / 2;
        int xEnd = size.getWidth() / 2;
        int yEnd = size.getHeight() / 2;
        switch (direction) {
            case LEFT:
                xStart = ((100 - offsetPercent) * size.getWidth()) / 100;
                xEnd = (offsetPercent * size.getWidth()) / 100;
                break;
            case RIGHT:
                xStart = (offsetPercent * size.getWidth()) / 100;
                xEnd = ((100 - offsetPercent) * size.getWidth()) / 100;
                break;
            case UP:
                yStart = ((100 - offsetPercent) * size.getHeight()) / 100;
                yEnd = (offsetPercent * size.getHeight()) / 100;
                break;
            case DOWN:
                yStart = (offsetPercent * size.getHeight()) / 100;
                yEnd = ((100 - offsetPercent) * size.getHeight()) / 100;
                break;
            default:
                throw new RuntimeException("Unknown direction!");
        }

        action
                .press(PointOption.point(xStart, yStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration)))
                .moveTo(PointOption.point(xEnd, yEnd))
                .release().perform();
    }

    public BufferedImage getScreenshot() {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            return ImageIO.read(screenshot);
        } catch (IOException e) {
            throw new RuntimeException("Failed to take screenshot.", e);
        }
    }
}
