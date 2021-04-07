package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import settings.Settings;

import java.util.concurrent.TimeUnit;

public class MobileTest {
    private AppiumDriverLocalService server;
    protected AppiumDriver<MobileElement> driver;

    @BeforeClass
    public void beforeSuite() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingAnyFreePort();
        serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");

        server = AppiumDriverLocalService.buildService(serviceBuilder);
        server.start();

        Settings settings = new Settings();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, settings.getPlatform());
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, settings.getDeviceName());
        caps.setCapability(MobileCapabilityType.UDID, settings.getUdid());
        caps.setCapability(MobileCapabilityType.APP, settings.getAppPath());

        // Allow debug more than 120 seconds
        if (settings.isDebug()) {
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3600);
        } else {
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        }

        // Handle web views
        String chromeDriverVersion = settings.getChromeDriverVersion();
        WebDriverManager.chromedriver().driverVersion(chromeDriverVersion).setup();
        String path = WebDriverManager.chromedriver().driverVersion(chromeDriverVersion).getDownloadedDriverPath();
        caps.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, path);

        driver = new AppiumDriver<MobileElement>(server.getUrl(), caps);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass()
    public void afterSuite() {
        driver.quit();
        server.stop();
    }
}