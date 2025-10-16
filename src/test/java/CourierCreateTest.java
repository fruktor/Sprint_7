
import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateModel;
import model.CourierLoginRequest;
import org.junit.Test;

import static data.TestData.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.*;

public class CourierCreateTest extends BaseApiTest {

    @Test
    @DisplayName("Ожидание ответа 201 при создании курьера")
    public void createNewCourierTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);

        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        deleteCourier(getCourierId(login));

    }

    @Test
    @DisplayName("Ожидание ошибки 409 при создании курьера с одинаковым логином")
    public void createTwoIndicalCourierTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);

        createCourier(courier);

        createCourier(courier)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        deleteCourier(getCourierId(login));

    }

    @Test
    @DisplayName("Ожидание ошибки 400 при создании курьера без логина")
    public void createCourierWithoutLoginTest() {
            CourierCreateModel courier = new CourierCreateModel(null, PASSWORD, FIRSTNAME);
            createCourier(courier)
                    .then()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Ожидание ошибки 400 при создании курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, null, FIRSTNAME);

        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));


    }

}
