package helpers;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.FileUtils.readStringFromFile;

public class ApiHelper {

    public static String getUserToken() {
        String data = readStringFromFile("src/test/resources/regress_in_login_data.json");

        return given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .log().body()
                .extract()
                .path("token");
    }
}
