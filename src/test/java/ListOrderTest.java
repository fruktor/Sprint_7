import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.getOrderList;

public class ListOrderTest extends BaseApiTest {

    @Test
    @DisplayName("Код ответа 200 при получение списка заказов")
    public void getListOrderTest() {

        getOrderList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
