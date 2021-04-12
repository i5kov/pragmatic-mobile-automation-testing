package wdio.tests;

import com.pragmatic.framework.base.MobileTest;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wdio.screens.HomeScreen;
import wdio.screens.LoginScreen;

import java.time.Duration;

public class SystemTests extends MobileTest {

    @BeforeMethod
    public void beforeMethod() {
        driver.resetApp();
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @Test
    public void testRunInBackground() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navigateTo();

        driver.runAppInBackground(Duration.ofSeconds(5));
        loginScreen.verifyLoaded();
    }

    @Test
    public void testRestartApp() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navigateTo();

        driver.resetApp();
        HomeScreen homeScreen = new HomeScreen(driver);
        homeScreen.verifyLoaded();
    }

    @Test
    public void testNavigateBack() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navigateTo();

        driver.navigate().back();
        HomeScreen homeScreen = new HomeScreen(driver);
        homeScreen.verifyLoaded();
    }

    @Test
    public void testRotateAppVariant1() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navigateTo();

        try {
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } catch (org.openqa.selenium.InvalidElementStateException ignored) {
        }
    }

    @Test(expectedExceptions = {org.openqa.selenium.InvalidElementStateException.class})
    public void testRotateAppVariant2() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navigateTo();
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }
}
