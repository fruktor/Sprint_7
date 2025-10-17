package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierCreateModel;
import model.CourierLoginRequest;

import static constants.ApiConstant.*;
import static io.restassured.RestAssured.given;


public class CourierSteps {

    @Step("Создание нового курьера")
    public static Response createCourier(CourierCreateModel courierCreateModel) {
       return given().log().all()
                .contentType(ContentType.JSON)
                .body(courierCreateModel)
                .when()
                .post(CREATE_COURIER_POST)
                .then()
                .extract().response();
    }

    @Step("Авторизация курьера")
    public static Response loginCourierRequest(CourierLoginRequest courierLoginRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierLoginRequest)
                .when()
                .post(LOGIN_COURIER_POST)
                .then()
                .extract().response();
    }

    @Step("Получение ID курьера")
    public static int getCourierId(CourierLoginRequest courierLoginRequest) {
        Response response = loginCourierRequest(courierLoginRequest);
        response
                .then().statusCode(200);

        return response.path("id");

    }

    @Step("Удаление курьера")
    public static Response deleteCourier(int courierId) {

        return given()
                .when()
                .delete(DELETE_COURIER + courierId)
                .then()
                .extract().response();

    }

}
