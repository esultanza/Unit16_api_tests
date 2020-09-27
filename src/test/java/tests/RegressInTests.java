package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static helpers.ApiHelper.getUserToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.FileUtils.readStringFromFile;

public class RegressInTests {

    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void successSingleResourceTest() {
        given()
                .when()
                .get("/unknown/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("ad.company", is("StatusCode Weekly"));
    }

    @Test
    void successCreateTest() {
        String data = """
                {"name": "taya",
                "job": "qa"}
                """;

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("id", is(notNullValue()));
    }

    @Test
    void successPatchTest() {
        String data = """
                {"name": "Taya",
                "job": "engineer"}
                """;

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users/2")
                .then()
                .statusCode(201)
                .log().body()
                .body("job", is("engineer"));
    }

    @Test
    void successRegisterWithDataFromResourcesTest() {
        String data = readStringFromFile("src/test/resources/regress_in_login_data.json");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", is(notNullValue()));
    }

    @Test
    void unsuccessLoginTest() {
        String data = "{\"email\": \"peter@pen\"}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .log().body();
    }

    @Test
    void successTokenTest() {
        String token = getUserToken();

        System.out.println(token);
    }

}
