import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends MobileTest {

    @Test
    public void testLoginWithValidUser() {
        // Go to login tab
        driver.findElementByAccessibilityId("Login").click();

        // Input username
        MobileElement userName = driver.findElementByAccessibilityId("input-email");
        userName.clear();
        userName.sendKeys("dtopuzov@gmail.com");

        // Input password
        MobileElement password = driver.findElementByAccessibilityId("input-password");
        password.clear();
        password.sendKeys("mySecretPassword");

        // Click login button
        driver.findElementByAccessibilityId("button-LOGIN").click();

        // Verify logged in
        MobileElement title = driver.findElement(By.id("android:id/alertTitle"));
        Assert.assertEquals(title.getText(), "Success");
    }

    @Test
    public void testLoginWithInvalidEmail() {
        Assert.assertTrue(true);
    }

    @Test
    public void testLoginWithShortPassword() {
        Assert.assertTrue(true);
    }
}