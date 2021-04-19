package rbb.pages;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends MobileScreen {
    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    public void visit() {
        driver.navigate().to("https://online.rbb.bg");
    }

    public WebElement getDownloadButton() {
        return driver.findElement(By.cssSelector("a.smartbanner__button"));
    }

    public WebElement getErrorElement() {
        By locator = By.cssSelector(".validation-msg span[class='value']");
        return driver.findElement(locator);
    }

    public WebElement getLang() {
        return driver.findElement(By.cssSelector(".i-language"));
    }

    public WebElement getLoginButton() {
        return driver.findElement(By.cssSelector("fieldset button[type='submit']"));
    }

    public void login(String user, String password) {
        By nameLocator = By.cssSelector("fieldset input[type='Text']");
        By passLocator = By.cssSelector("fieldset input[type='Password']");
        driver.findElement(nameLocator).clear();
        driver.findElement(nameLocator).sendKeys(user);
        driver.findElement(passLocator).clear();
        driver.findElement(passLocator).sendKeys(user);
        getLoginButton().click();
    }
}
