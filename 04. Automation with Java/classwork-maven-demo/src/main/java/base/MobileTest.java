package base;

import appium.Client;
import appium.Server;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import settings.Settings;

public class MobileTest {
    private Server server;
    private Client client;
    protected AppiumDriver<MobileElement> driver;

    @BeforeClass
    public void beforeSuite() {
        Settings settings = new Settings();
        server = new Server(settings);
        server.start();

        client = new Client(settings);
        client.start(server.getUrl());
        driver = client.getDriver();
    }

    @AfterClass()
    public void afterSuite() {
        client.stop();
        server.stop();
    }
}