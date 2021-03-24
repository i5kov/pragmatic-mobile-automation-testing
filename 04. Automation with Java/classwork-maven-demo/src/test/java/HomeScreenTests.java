import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class HomeScreenTests extends MobileTest {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Navigate to home page screen.");
    }

    @Test
    public void test1() {
        assertTrue(true);
    }

    @Test
    public void test2() {
        assertTrue(true);
    }
}
