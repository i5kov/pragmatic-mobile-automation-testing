package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginScreen {
    private AppiumDriver<MobileElement> driver;

    public LoginScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.findElementByAccessibilityId("Login").click();
    }

    public void login(String user, String password) {
        // Input username
        MobileElement userName = driver.findElementByAccessibilityId("input-email");
        userName.clear();
        userName.sendKeys(user);

        // Input password
        MobileElement passwordInput = driver.findElementByAccessibilityId("input-password");
        passwordInput.clear();
        passwordInput.sendKeys(password);

        // Click login button
        driver.findElementByAccessibilityId("button-LOGIN").click();
    }

    public void verifySuccessfulLogin(){
        MobileElement title = driver.findElement(By.id("android:id/alertTitle"));
        Assert.assertEquals(title.getText(), "Success");
    }

    public void verifyErrorMessageDisplayed(String message) {
        By locator = By.xpath("//*[@text='" + message + "']");
        MobileElement error = driver.findElement(locator);
        Assert.assertTrue(error.isDisplayed());
    }
}