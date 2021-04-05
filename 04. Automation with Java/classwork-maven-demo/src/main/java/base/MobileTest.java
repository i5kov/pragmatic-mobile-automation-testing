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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.lang.management.ManagementFactory;
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

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "MyDevice");
        caps.setCapability(MobileCapabilityType.UDID, "RF8N715NJ0B");
        caps.setCapability(MobileCapabilityType.APP, "/Users/topuzov/git/pragmatic-mobile-testing/appium/testapps/Android-NativeDemoApp-0.2.1.apk");

        if (ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp")) {
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3600);
        } else {
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        }

        WebDriverManager.chromedriver().driverVersion("89.0.4389.23").setup();
        String path = WebDriverManager.chromedriver().driverVersion("89.0.4389.23").getDownloadedDriverPath();
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