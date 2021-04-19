package wdio.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class WebScreen {
    private final AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    public WebScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    @Step("Go to WebView tab")
    public void navigateTo() {
        driver.findElement(MobileBy.AccessibilityId("WebView")).click();

        await().atMost(30, SECONDS).until(() -> driver.getContextHandles().size() > 1);

        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.toLowerCase().contains("web")) {
                driver.context(contextName);
                await().atMost(30, SECONDS).until(() -> driver.getPageSource().contains("html"));
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
