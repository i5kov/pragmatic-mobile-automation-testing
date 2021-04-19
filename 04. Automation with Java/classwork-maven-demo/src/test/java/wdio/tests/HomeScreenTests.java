package wdio.tests;

import com.pragmatic.framework.base.MobileTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wdio.screens.HomeScreen;

public class HomeScreenTests extends MobileTest {
    private HomeScreen homeScreen;

    @BeforeMethod
    public void beforeMethod() {
        driver.resetApp();
        homeScreen = new HomeScreen(driver);
    }

    @Test
    public void testHomeLooksOK() {
        homeScreen.verifyLoaded();
        homeScreen.match("home");
    }
}
