package wdio.screens;

import com.pragmatic.framework.base.MobileScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import wdio.widgets.Footer;

public class SwipeScreen extends MobileScreen {
    public Footer footer;

    public SwipeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.footer = new Footer(driver);
    }
}
