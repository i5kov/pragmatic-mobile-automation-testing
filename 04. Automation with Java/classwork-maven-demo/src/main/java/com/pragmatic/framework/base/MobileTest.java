package com.pragmatic.framework.base;

import com.pragmatic.framework.appium.Client;
import com.pragmatic.framework.appium.Server;
import com.pragmatic.framework.settings.Settings;
import com.pragmatic.framework.utils.FileSystem;
import com.pragmatic.framework.utils.Image;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MobileTest {
    private static Server server;
    private static Client client;
    protected static AppiumDriver<MobileElement> driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        Settings settings = new Settings();
        server = new Server(settings);
        server.start();

        client = new Client(settings);
        client.start(server.getUrl());
        driver = client.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getMethod().getMethodName();
            collectArtifacts(testName);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        client.stop();
        server.stop();
    }

    private void collectArtifacts(String testName) {
        String resultsFolder = System.getProperty("user.dir")
                + File.separator + "target" + File.separator + "surefire";
        String logsFolder = resultsFolder + File.separator + "logs";
        String screenshotsFolder = resultsFolder + File.separator + "screenshots";

        // Get screenshot
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage image = ImageIO.read(screenshot);
            Image.save(image, screenshotsFolder + File.separator + testName + ".png");
            Allure.addAttachment("Screenshot on test fail", "image/png",
                    Image.bufferedImageToInputStream(image), ".png");
        } catch (IOException ignored) {
        }

        // Get page source
        try {
            String pageSource = driver.getPageSource();
            FileSystem.writeStringToFile(pageSource, logsFolder + File.separator + testName + ".xml");
        } catch (IOException ignored) {
        }
    }
}
