package wdio.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.support.CacheLookup;
import org.testng.Assert;
import wdio.widgets.Footer;

public class LoginScreen extends MobileScreen {
    public Footer footer;

    @CacheLookup()
    @AndroidFindBy(accessibility = "input-email")
    @iOSXCUITFindBy(accessibility = "input-email")
    private MobileElement inputEmail;

    @CacheLookup()
    @AndroidFindBy(accessibility = "input-password")
    @iOSXCUITFindBy(accessibility = "input-password")
    private MobileElement inputPassword;

    @CacheLookup()
    @AndroidFindBy(accessibility = "button-LOGIN")
    @iOSXCUITFindBy(accessibility = "button-LOGIN")
    private MobileElement loginButton;

    @AndroidFindBy(id = "android:id/alertTitle")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Success\"`]")
    private MobileElement successMessage;

    public LoginScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.footer = new Footer(driver);
    }

    @Step("Go to Login tab")
    public void navigateTo() {
        footer.navigateTo("Login");
        verifyLoaded();
    }

    @Step("Verify Login screen loaded")
    public void verifyLoaded() {
        Assert.assertTrue(loginButton.isDisplayed());
    }

    @Step("Login with '{user}:{password}'")
    public void login(String user, String password) {
        inputEmail.clear();
        inputEmail.sendKeys(user);
        inputPassword.clear();
        inputPassword.sendKeys(password);
        loginButton.click();
    }

    @Step("Verify user logged in successfully")
    public void verifySuccessfulLogin() {
        Assert.assertTrue(successMessage.isDisplayed());
    }

    @Step("Verify '{message}' message displayed.")
    public void verifyErrorMessageDisplayed(String message) {
        MobileElement error = findByText(message);
        Assert.assertTrue(error.isDisplayed());
    }
}
