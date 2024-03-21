package org.logico;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
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
    @TestSecurity(user = "johndoe", roles = {"Admin-1"})
    void testGetPrivilegeByIdSuccess() {
        PrivilegeDto dto = PrivilegeDto.builder().id(1).name("View Role").build();
        given()
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
    @TestSecurity(user = "johndoe", roles = {"Admin-1"})
    void testGetPrivilegeByIdFailure() {
        given()
                .when()
                .get("/api/v1/privilege/" + Integer.MAX_VALUE)
                .then()
                .statusCode(400)
                .body("status", is(400))
                .body("message", is("Privilege with id " + Integer.MAX_VALUE + " was not found"));

    }


}
