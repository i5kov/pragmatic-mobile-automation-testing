package wdio.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Set;

public class WebScreen {
    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    public WebScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    @Step("Go to WebView tab")
    public void navigateTo() {
        driver.findElement(MobileBy.AccessibilityId("WebView")).click();
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.toLowerCase().contains("web")) {
                driver.context(contextName);
                wait = new WebDriverWait(driver, 30);
            }
        }
    }

    public void exit() {
        driver.context("NATIVE_APP");
    }

    @Step("Search for '{text}'")
    public void search(String text) {
        By searchButtonLocator = By.cssSelector(".DocSearch-Button");
        wait.until(ExpectedConditions.presenceOfElementLocated(searchButtonLocator)).click();

        By searchInputLocator = By.cssSelector(".DocSearch-Input");
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(searchInputLocator));
        input.clear();
        input.sendKeys(text + Keys.ENTER);
    }

    @Step("Verify result contains '{text}' text")
    public void verifyResultExist(String text) {
        By locator = By.xpath("//span[@class='DocSearch-Hit-title' and .='" + text + "']");
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Assert.assertTrue(result.isDisplayed());
    }
}
