package wdio;

import base.MobileTest;
import enums.Direction;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.SwipeScreen;
import screens.WebScreen;

public class SwipeTests extends MobileTest {

    private SwipeScreen swipeScreen;

    @BeforeMethod
    public void beforeMethod() {
        driver.resetApp();
        swipeScreen = new SwipeScreen(driver);
        swipeScreen.navigateTo();
    }

    @Test
    public void smokeTest() {
        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.swipe(Direction.RIGHT);
        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.swipe(Direction.RIGHT);
        swipeScreen.swipe(Direction.RIGHT);
        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.swipe(Direction.RIGHT);
        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.swipe(Direction.RIGHT);
        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.swipe(Direction.LEFT);
    }
}