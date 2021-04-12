package wdio.tests;

import com.pragmatic.framework.base.MobileTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wdio.screens.WebScreen;

public class WebViewTests extends MobileTest {

    private WebScreen webScreen;

    @BeforeMethod
    public void beforeMethod() {
        driver.resetApp();
        webScreen = new WebScreen(driver);
        webScreen.navigateTo();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        webScreen.exit();
    }

    @Test
    public void searchTest() {
        webScreen.search("android");
        webScreen.verifyResultExist("Android UiAutomator");
    }
}
