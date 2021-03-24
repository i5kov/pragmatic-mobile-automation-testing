import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class MobileTest {

    @BeforeSuite
    public void beforeSuite() {
        // Start Emulator
        System.out.println("Start emulator.");
    }

    @AfterSuite
    public void afterSuite() {
        // Stop Emulator
        System.out.println("Stop emulator");
    }

}
