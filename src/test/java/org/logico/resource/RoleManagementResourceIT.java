package org.logico.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.logico.security.model.request.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class RoleManagementResourceIT {

    @Test
    public void testGetAllRoles() {
        final UserCredentials userCredentials = UserCredentials.builder()
                .username("johndoe")
                .password("123")
                .build();
        String token = given()
                .contentType("application/json")
                .body(userCredentials)
                .when()
                .post("api/v1/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("dtoList.size()", is(3));
    }
}
