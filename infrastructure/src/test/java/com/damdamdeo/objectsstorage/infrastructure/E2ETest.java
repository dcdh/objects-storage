package com.damdamdeo.objectsstorage.infrastructure;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Headers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class E2ETest {

    @Test
    @Order(0)
    public void should_store_object() throws Exception {
        given()
                .multiPart("content", getFileFromResource("given/2021-08-19-211905.jpg"))
                .multiPart("fullPath", "/e2e/object")
                .when()
                .post("/objects/upload")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @Order(1)
    public void should_get_object() {
        final Headers headers = given()
                .queryParam("objectLocation", "/e2e/object")
                .when()
                .get("/objects")
                .then()
                .log().headers()
                .statusCode(200)
                .extract()
                .headers();
        assertThat(headers.size()).isEqualTo(45);
    }

    private File getFileFromResource(final String fileName) throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        return new File(resource.toURI());
    }

}
