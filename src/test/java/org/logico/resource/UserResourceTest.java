package org.logico.resource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserResourceTest {

    @Test
    void getUserByUsername() {
        LoginRequest loginRequest = new LoginRequest("johndoe", "123");

        Response responseToken = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/v1/login")
                .then()
                .log().all()
                .extract()
                .response();

        responseToken.then().statusCode(200);

        TokenResponce tokenResponce = responseToken.as(TokenResponce.class);

        assertNotNull(tokenResponce, "Token response is null");

        String token = tokenResponce.getToken();

        assertNotNull(token, "Token is null or empty");

        RestAssured.baseURI = "http://localhost:8080";

        Response responseUser = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/johndoe")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        responseUser.then().statusCode(200);

        responseUser.then().body("username", equalTo("johndoe")).log().all();
        responseUser.then().body("email", equalTo("johndoe@example.com")).log().all();
    }
}