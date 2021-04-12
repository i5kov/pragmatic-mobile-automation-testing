package wdio.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AutomationName;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginScreen extends MobileScreen {
    public LoginScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    @Step("Go to Login tab")
    public void navigateTo() {
        driver.findElement(MobileBy.AccessibilityId("Login")).click();
        verifyLoaded();
    }

    @Step("Verify Login screen loaded")
    public void verifyLoaded() {
        MobileElement loginButton = driver.findElement(MobileBy.AccessibilityId("button-LOGIN"));
        Assert.assertTrue(loginButton.isDisplayed());
    }


    @Step("Login with '{user}:{password}'")
    public void login(String user, String password) {
        // Input username
        MobileElement userName = driver.findElement(MobileBy.AccessibilityId("input-email"));
        userName.clear();
        userName.sendKeys(user);

        // Input password
        MobileElement passwordInput = driver.findElement(MobileBy.AccessibilityId("input-password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        // Click login button
        driver.findElement(MobileBy.AccessibilityId("button-LOGIN")).click();
    }

    @Step("Verify user logged in successfully")
    public void verifySuccessfulLogin() {
        String automation = driver.getCapabilities().getCapability("automationName").toString();
        if (automation.equalsIgnoreCase(AutomationName.ANDROID_UIAUTOMATOR2)) {
            MobileElement title = driver.findElement(By.id("android:id/alertTitle"));
            Assert.assertEquals(title.getText(), "Success");
        } else {
            MobileElement message = findByText("You are logged in!");
            Assert.assertTrue(message.isDisplayed());
        }
    }

    @Step("Verify '{message}' message displayed.")
    public void verifyErrorMessageDisplayed(String message) {
        MobileElement error = findByText(message);
        Assert.assertTrue(error.isDisplayed());
    }
}
