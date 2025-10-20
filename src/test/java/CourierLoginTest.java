import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.CourierCreateModel;
import model.CourierLoginRequest;
import org.junit.*;

import static data.TestData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.*;

public class CourierLoginTest {


    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
        CourierCreateModel courier = new CourierCreateModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
    }

    @Test
    @DisplayName("Ожидание ответа 200 при авторизации курьера")
    public void authorizationCourierTest() {
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);

        int courierId = getCourierId(login);

        loginCourierRequest(login)
                .then()
                .statusCode(200)
                .body("id", equalTo(courierId));

    }

    @Test
    @DisplayName("Ожидание ошибки 400 при авторазации курьера без логина")
    public void authorizationCourierWithoutLoginTest() {

        CourierLoginRequest withoutPassword = new CourierLoginRequest(null, PASSWORD);

        loginCourierRequest(withoutPassword)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));


    }

    @Test
    @DisplayName("Ожидание ошибки 400 при авторазации курьера без пароля")
    public void authorizationCourierWithoutPasswordTest() {

        CourierLoginRequest withoutPassword = new CourierLoginRequest(LOGIN, null);

        loginCourierRequest(withoutPassword)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));


    }

    @Test
    @DisplayName("Ожидание ошибки 404 при авторизации с несуществующими данными")
    public void authorizationCourierWithNonExistentDataTest() {
        CourierLoginRequest login = new CourierLoginRequest(LOGIN + "fake", PASSWORD + "fake");

        loginCourierRequest(login)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
        }

      @Test
      @DisplayName("Ожидания ошибки 404 при авторизации с не правильным паролем")
      public void authorizationCourierWithWrongPassword() {
          CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD + "fake");

          loginCourierRequest(login)
                  .then()
                  .statusCode(404)
                  .body("message", equalTo("Учетная запись не найдена"));

      }


    @After
    public void clean() {
        CourierLoginRequest login = new CourierLoginRequest(LOGIN, PASSWORD);
        deleteCourier(getCourierId(login));
    }
}
