
import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateModel;
import model.CourierLoginRequest;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.*;

public class CourierCreateTest extends BaseApiTest {



    @Test
    @DisplayName("Ожидание ответа 201 при создании курьера")
    public void createNewCourierTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);

        createCourier(courier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Ожидание ошибки 409 при создании курьера с одинаковым логином")
    public void createTwoIndicalCourierTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);

        createCourier(courier);

        createCourier(courier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Ожидание ошибки 400 при создании курьера без логина")
    public void createCourierWithoutLoginTest() {
            CourierCreateModel courier = new CourierCreateModel(null, PASSWORD, FIRSTNAME);

            createCourier(courier)
                    .then()
                    .statusCode(SC_BAD_REQUEST)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Ожидание ошибки 400 при создании курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, null, FIRSTNAME);

        createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));


    }

    @After
    public void clean() {
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);
        try {
            deleteCourier(getCourierId(login));
        } catch (AssertionError | NullPointerException e) {

        }
    }

}
