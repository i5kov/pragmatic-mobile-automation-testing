package screens;

import enums.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseScreen {
    protected AppiumDriver<MobileElement> driver;
    protected WebDriverWait wait;

    public BaseScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public void swipe(Direction direction) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int xStart = size.getWidth() / 2;
        int yStart = size.getHeight() / 2;
        int xEnd = size.getWidth() / 2;
        int yEnd = size.getHeight() / 2;
        switch (direction) {
            case LEFT:
                xStart = (4 * size.getWidth()) / 5;
                xEnd = size.getWidth() / 5;
                break;
            case RIGHT:
                xStart = size.getWidth() / 5;
                xEnd = (4 * size.getWidth()) / 5;
                break;
            case UP:
                yStart = (4 * size.getHeight()) / 5;
                yEnd = size.getHeight() / 5;
                break;
            case DOWN:
                yStart = size.getHeight() / 5;
                yEnd = (4 * size.getHeight()) / 5;
                break;
            default:
                throw new RuntimeException("Unknown direction!");
        }

        action
                .press(PointOption.point(xStart, yStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(xEnd, yEnd))
                .release().perform();
    }
}