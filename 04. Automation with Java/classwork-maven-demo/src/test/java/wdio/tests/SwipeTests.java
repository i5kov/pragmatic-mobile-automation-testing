package wdio.tests;

import com.pragmatic.framework.base.MobileTest;
import com.pragmatic.framework.enums.Direction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wdio.screens.SwipeScreen;

public class SwipeTests extends MobileTest {

    private SwipeScreen swipeScreen;

    @BeforeMethod
    public void beforeMethod() {
        driver.resetApp();
        swipeScreen = new SwipeScreen(driver);
        swipeScreen.footer.navigateTo("Swipe");
    }

    @Test
    public void smokeTest() {
        swipeScreen.verifyTextVisible("FULLY OPEN SOURCE");

        swipeScreen.swipe(Direction.LEFT);
        swipeScreen.verifyTextVisible("CREAT COMMUNITY");

        swipeScreen.swipe(Direction.RIGHT);
        swipeScreen.verifyTextVisible("FULLY OPEN SOURCE");
    }
}
