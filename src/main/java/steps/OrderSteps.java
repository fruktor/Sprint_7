package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps  {

    @Step("Создание заказа")
    public static Response createOrder(OrderModel orderModel) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract().response();
    }

    @Step("Получение списка заказа")
    public static Response getOrderList() {
        return given()
                .get("/api/v1/orders")
                .then()
                .extract().response();
    }

}
