import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static data.TestData.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {
    private List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] color() {
        return new Object[][] {
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @Test
    @DisplayName("Ожидание ответа 201, при создании заказа с разными параметрами цвета самоката")
    public void createOrderTest() {
        OrderModel data = new OrderModel(FIRSTNAME, LASTNAME, ADDRESS, METROSTATION, PHONE, RENTTIME, DELIVERYDATE, COMMENT, color);

        createOrder(data)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    }



