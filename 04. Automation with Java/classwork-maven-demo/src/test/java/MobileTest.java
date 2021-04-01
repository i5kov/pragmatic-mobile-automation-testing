import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class MobileTest {
    private AppiumDriverLocalService server;
    protected AppiumDriver<MobileElement> driver;

    @BeforeSuite
    public void beforeSuite() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingAnyFreePort();
        serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");

        server = AppiumDriverLocalService.buildService(serviceBuilder);
        server.start();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "MyDevice");
        caps.setCapability("udid", "RF8N715NJ0B");
        caps.setCapability("app", "/Users/topuzov/git/pragmatic-mobile-testing/appium/testapps/Android-NativeDemoApp-0.2.1.apk");
        driver = new AppiumDriver<MobileElement>(server.getUrl(), caps);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterSuite()
    public void afterSuite() {
        driver.quit();
        server.stop();
    }
}