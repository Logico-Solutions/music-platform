package org.logico.resource;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class TrackingUserResourceTest {

    @Test
    void createTrackingUser() {
    }

    @Test
    void showTrackingUsers() {
        given()
                .get("local/users")
                .then()
                .statusCode(200)
                .log().all()
                .contentType(ContentType.JSON)
                .body("userEmail", is("example1@ex.com"));
    }
    @Test
    void updateTrackingUser() {
        given()
                .patch("/update/email");
    }

    @Test
    void updateSwitchTracking() {
        given()
                .patch("/local/update/switch");

    }

    @Test
    void deleteTrackingUser() {
        given()
                .delete("/delete");
    }
}