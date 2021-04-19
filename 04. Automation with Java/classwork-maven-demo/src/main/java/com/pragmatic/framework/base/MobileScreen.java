package com.pragmatic.framework.base;

import com.pragmatic.framework.enums.Direction;
import com.pragmatic.framework.settings.Settings;
import com.pragmatic.framework.utils.Image;
import com.pragmatic.framework.utils.ImageComparisonResult;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class MobileScreen {
    protected AppiumDriver<MobileElement> driver;

    public MobileScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
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
        return driver.findElement(locator);
    }

    @Step("Verify '{text}' text is visible")
    public void verifyTextVisible(String text) {
        Assert.assertTrue(findByText(text).isDisplayed());
    }

    public void swipe(Direction direction) {
        swipe(direction, 20);
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

    @SuppressWarnings("unchecked")
    public Rectangle getViewPortRectangle() {
        Map<String, Object> caps = driver.getSessionDetails();
        Map<String, Object> viewportRect = (Map<String, Object>) caps.get("viewportRect");
        int x = Integer.parseInt(viewportRect.get("left").toString());
        int y = Integer.parseInt(viewportRect.get("top").toString());
        int width = Integer.parseInt(viewportRect.get("width").toString()) + x;
        int height = Integer.parseInt(viewportRect.get("height").toString()) + y;
        return new Rectangle(x, y, height, width);
    }

    public void match(String image) {
        match(image, 0.01);
    }

    public void match(String image, double tolerance) {
        match(image, tolerance, 10);
    }

    @Step("Compare current screen with '{0}' image")
    public void match(String image, double tolerance, int timeout) {
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString().toLowerCase();
        String imageName = deviceName.replace(" ", "") + File.separator + image + ".png";
        String projectRoot = System.getProperty("user.dir");
        String resources = projectRoot + File.separator + "src" + File.separator + "test" + File.separator + "resources";
        String expectedImagePath = resources + File.separator + imageName;

        if (new File(expectedImagePath).exists()) {
            ImageComparisonResult result;

            boolean match = false;
            long startTime = System.currentTimeMillis();
            do {
                result = compareScreen(expectedImagePath);
                if (result.diffPercent <= tolerance) {
                    match = true;
                }
            } while (!match && System.currentTimeMillis() - startTime < timeout * 1000L);

            // Save actual and diff image
            if (result.diffPercent > tolerance) {
                Image.save(result.actualImage, expectedImagePath.replace(".png", "_actual.png"));
                Image.save(result.diffImage, expectedImagePath.replace(".png", "_diff.png"));
            }

            // Verify result
            Assert.assertTrue(result.diffPercent <= tolerance,
                    String.format("Current screen does not match '%s' image.", image));
        } else {
            BufferedImage actualImage = getScreenshot();
            Image.save(actualImage, expectedImagePath);
        }
    }

    private ImageComparisonResult compareScreen(String expectedImagePath) {
        BufferedImage actualImage = getScreenshot();
        Rectangle viewPort = getViewPortRectangle();
        BufferedImage expectedImage = Image.fromFile(expectedImagePath);

        // On new iOS devices (iPhone 11, iPhone X*) viewport rectangle is way smaller than actual size
        if (actualImage.getWidth() > viewPort.getWidth()) {
            viewPort.setWidth(actualImage.getWidth());
        }
        if (actualImage.getHeight() > viewPort.getHeight()) {
            viewPort.setHeight(actualImage.getHeight());
        }

        return Image.compare(actualImage, expectedImage, viewPort, 10);
    }
}
