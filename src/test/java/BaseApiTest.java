import io.restassured.RestAssured;
import org.junit.BeforeClass;

import static data.TestData.URL;

public class BaseApiTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = URL;

    }
}
