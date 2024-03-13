package org.logico;

import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.logico.resource.PrivilegeResource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(PrivilegeResource.class)
public class PrivilegeResourceTest {

    @Test
    public void testGetPrivilegesFirstPage() {
        given()
                .queryParam("pageNumber", 0)
                .queryParam("rowCount", 2)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(200)
                .body("privilegeDtoList.size()", is(2));
    }

    @Test
    public void testGetPrivilegesSecondPage() {
        given()
                .queryParam("pageNumber", 1)
                .queryParam("rowCount", 2)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(200)
                .body("privilegeDtoList.size()", is(2));
    }

    @Test
    public void testGetPrivilegesThirdPage() {
        given()
                .queryParam("pageNumber", 2)
                .queryParam("rowCount", 2)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(200)
                .body("privilegeDtoList.size()", is(2));
    }

    @Test
    public void testGetPrivilegesWithInvalidPageNumber() {
        given()
                .queryParam("pageNumber", -1)
                .queryParam("rowCount", 2)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetPrivilegesWithInvalidRowCount() {
        given()
                .queryParam("pageNumber", 1)
                .queryParam("rowCount", 0)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetPrivilegesPageBeyondRange() {
        given()
                .queryParam("pageNumber", 10)
                .queryParam("rowCount", 2)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(200)
                .body("privilegeDtoList.size()", is(0));
    }

    @Test
    public void testGetPrivilegesWithLargeRowCount() {
        given()
                .queryParam("pageNumber", 0)
                .queryParam("rowCount", 100)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(200)
                .body("privilegeDtoList.size()", is(6));
    }

    @Test
    public void testGetPrivilegesSortedByIdAsc() {
        given()
                .queryParam("pageNumber", 0)
                .queryParam("rowCount", 4)
                .queryParam("direction", Direction.Ascending)
                .when()
                .get("/list/paginated-sorted")
                .then()
                .statusCode(200)
                .body("privilegeDtoList.get(0).id", is(1))
                .body("privilegeDtoList.get(1).id", is(2))
                .body("privilegeDtoList.get(2).id", is(3))
                .body("privilegeDtoList.get(3).id", is(4));
    }
}
