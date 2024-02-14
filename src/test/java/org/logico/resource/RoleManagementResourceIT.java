package org.logico.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.logico.security.model.request.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class RoleManagementResourceIT {

    final UserCredentials johndoeCredentials = UserCredentials.builder()
            .username("johndoe")
            .password("123")
            .build();
    final UserCredentials subadminCredentials = UserCredentials.builder()
            .username("subadmin")
            .password("123")
            .build();

    @Test
    public void testGetAllRoles() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("dtoList.size()", is(3));
    }

    @Test
    public void testGetPartOfRoles() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .queryParam("page", 1)
                .queryParam("size", 2)
                .queryParam("sort-by", "privilegeAssignments")
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(200)
                .body("dtoList.size()", is(1));
    }

    @Test
    public void testGetRolesNoContent() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .queryParam("page", 1)
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetRolesWrongPage() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .queryParam("page", -1)
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetRolesWrongSize() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .queryParam("size", 0)
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetRolesWrongSortBy() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(johndoeCredentials))
                .queryParam("sort-by", "test")
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUnauthorizedGetRoles() {
        given()
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(401);
    }

    @Test
    public void testForbiddenGetRoles() {
        given()
                .header("Authorization", "Bearer " + getTokenForTest(subadminCredentials))
                .when()
                .get("api/v1/roles")
                .then()
                .statusCode(403);
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
