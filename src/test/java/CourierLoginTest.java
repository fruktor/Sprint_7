import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateModel;
import model.CourierLoginRequest;
import org.junit.Test;

import static data.TestData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.*;

public class CourierLoginTest extends BaseApiTest {

    @Test
    @DisplayName("Ожидание ответа 200 при авторизации курьера")
    public void authorizationCourierTest() {
        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);

        createCourier(courier);
        int courierId = getCourierId(login);

        loginCourierRequest(login)
                .then()
                .statusCode(200)
                .body("id", equalTo(courierId));

        deleteCourier(courierId);
    }

    @Test
    @DisplayName("Ожидание ошибки 400 при авторазации курьера без логина")
    public void authorizationCourierWithoutLoginTest() {

        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginRequest withoutPassword = new CourierLoginRequest(null, PASSWORD);
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);

        createCourier(courier);

        loginCourierRequest(withoutPassword)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        deleteCourier(getCourierId(login));
    }

    @Test
    @DisplayName("Ожидание ошибки 400 при авторазации курьера без пароля")
    public void authorizationCourierWithoutPasswordTest() {

        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginRequest withoutPassword = new CourierLoginRequest(LOGIN, null);
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);

        createCourier(courier);

        loginCourierRequest(withoutPassword)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        deleteCourier(getCourierId(login));
    }

    @Test
    @DisplayName("Ожидание ошибки 404 при авторизации с несуществующими данными")
    public void authorizationCourierWithNonExistentData() {
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);


        loginCourierRequest(login)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
        }

}
