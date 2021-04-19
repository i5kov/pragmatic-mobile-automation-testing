package rbb.tests;

import com.pragmatic.framework.base.MobileTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rbb.pages.HomePage;

public class SmokeTests extends MobileTest {
    private HomePage homePage;

    @BeforeMethod
    public void beforeMethod() {
        homePage = new HomePage(driver);
        homePage.visit();
    }

    @Test
    public void testMobileAppSuggestion() {
        WebElement downloadButton = homePage.getDownloadButton();
        String actual = downloadButton.getAttribute("href");
        Assert.assertEquals(actual, "https://goo.gl/6Ug8p4");
    }

    @Test
    public void testLoginInvalidUser() {
        homePage.login("invalidUser", "invalidPass");
        WebElement error = homePage.getErrorElement();
        String actual = error.getText();
        String expected = "НЕВАЛИДНО ПОТРЕБИТЕЛСКО ИМЕ ИЛИ ПАРОЛА";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testSwitchToEN() {
        homePage.getLang().click();
        String actual = homePage.getLoginButton().getText().trim();
        Assert.assertEquals(actual, "Login");
    }

    @Test(enabled = false)
    public void testNewRegistration() {
    }

    @Test(enabled = false)
    public void testForgottenPassword() {
    }
}
