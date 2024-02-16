package org.logico.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.logico.security.model.request.UserCredentials;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class UserResourceTest {
    final UserCredentials johndoeCredentials = new UserCredentials("johndoe","123");

    @Test
    public void testGetUserSuccess() {
        given()
                .when()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .get("api/v1/me")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("username", is("johndoe"));
    }

    @Test
    public void testGetUserUnauthorized() {
        given()
                .when()
                .get("api/v1/me")
                .then()
                .statusCode(401);
    }

    private String getTokenForTest(UserCredentials userCredentials) {
        return given()
                .contentType("application/json")
                .body(userCredentials)
                .when()
                .post("api/v1/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }
}