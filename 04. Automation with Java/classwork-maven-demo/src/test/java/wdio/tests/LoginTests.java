package wdio.tests;

import com.pragmatic.framework.base.MobileTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wdio.screens.LoginScreen;

public class LoginTests extends MobileTest {
    private LoginScreen loginScreen;

    @BeforeMethod
    public void beforeMethod() {
        driver.resetApp();
        loginScreen = new LoginScreen(driver);
        loginScreen.navigateTo();
    }

    @Test
    public void testLogin() {
        loginScreen.login("dtopuzov@gmail.com", "12345678");
        loginScreen.verifySuccessfulLogin();
    }

    @Test
    public void testLoginWithInvalidEmail() {
        loginScreen.login("dtopuzov", "12345678");
        loginScreen.verifyErrorMessageDisplayed("Please enter a valid email address");
    }

    @Test
    public void testLoginWithShortPassword() {
        loginScreen.login("dtopuzov@gmail.com", "123");
        loginScreen.verifyErrorMessageDisplayed("Please enter at least 8 characters");
    }
}
