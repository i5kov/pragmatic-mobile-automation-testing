import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LoginTests extends MobileTest {


    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Navigate to login page.");
    }

    @Test
    public void loginTest1() {
        assertTrue(true);
    }

    @Test
    public void loginTest2() {
        assertTrue(true);
    }

}
