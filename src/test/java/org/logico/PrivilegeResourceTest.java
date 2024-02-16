package org.logico;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.logico.dto.PrivilegeDto;
import org.logico.security.model.request.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class PrivilegeResourceTest {

    final UserCredentials userCredentials = new UserCredentials("johndoe", "123");

    @Test
    void testGetPrivilegeByIdSuccess() {
        final String jwttoken = setupToken();
        PrivilegeDto dto = PrivilegeDto.builder().id(1).name("View Role").build();
        given()
                .header("Authorization", "Bearer " + jwttoken)
                .when()
                .get("/api/v1/privilege/1")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .and()
                .body("id", is(dto.getId()))
                .and()
                .body("name", is(dto.getName()));
    }

    @Test
    void testGetPrivilegeByIdFailure() {
        final String jwttoken = setupToken();
        given()
                .header("Authorization", "Bearer " + jwttoken)
                .when()
                .get("/api/v1/privilege/" + Integer.MAX_VALUE)
                .then()
                .statusCode(400)
                .body("status", is(400))
                .body("message", is("Privilege with id " + Integer.MAX_VALUE + " was not found"));

    }


    /**
     * technical method to get jwt token before testing endpoints that requires authentication
     *
     * @return String token
     */
    private String setupToken() {
        return given().
                contentType(ContentType.JSON)
                .body(userCredentials)
                .when()
                .post("api/v1/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

}
