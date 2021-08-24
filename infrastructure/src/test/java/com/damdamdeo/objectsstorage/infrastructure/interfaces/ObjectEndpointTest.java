package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.*;
import com.damdamdeo.objectsstorage.domain.usecase.GetStoredObjectCommand;
import com.damdamdeo.objectsstorage.domain.usecase.GetStoredObjectUseCase;
import com.damdamdeo.objectsstorage.domain.usecase.StoreNewObjectCommand;
import com.damdamdeo.objectsstorage.domain.usecase.StoreNewObjectUseCase;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ObjectEndpointTest {

    @InjectMock
    GetStoredObjectUseCase getStoredObjectUseCase;

    @InjectMock
    StoreNewObjectUseCase storeNewObjectUseCase;

    @Test
    public void should_api_store_new_object() throws Exception {
        // Given
        doReturn(new ObjectCreated(
                new ObjectLocation("/path_to_object/object"),
                new UUID(0,0).toString()
        )).when(storeNewObjectUseCase)
                .execute(new StoreNewObjectCommand(new ObjectLocation("/path_to_object/object"), "content".getBytes()));

        // When && Then
        given()
                .multiPart("content", getFileFromResource("given/fileToUpload"))
                .multiPart("fullPath", "/path_to_object/object")
                .when()
                .post("/objects/upload")
                .then()
                .log().all()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/objectCreated.json"))
                .body("objectLocation.fullPath", equalTo("/path_to_object/object"))
                .body("objectLocation.fileName", equalTo("object"))
                .body("versionId", equalTo("00000000-0000-0000-0000-000000000000"));

        verify(storeNewObjectUseCase, times(1)).execute(any());
    }

    @Test
    public void should_api_store_new_object_fail_when_metadata_is_not_extractable() throws Exception {
        // Given
        doThrow(new UnableToExtractMetadataException())
                .when(storeNewObjectUseCase).execute(any());

        // When && Then
        given()
                .multiPart("content", getFileFromResource("given/fileToUpload"))
                .multiPart("fullPath", "/path_to_object/object")
                .when()
                .post("/objects/upload")
                .then()
                .log().all()
                .statusCode(500);

        verify(storeNewObjectUseCase, times(1)).execute(any());
    }

    @Test
    public void should_api_store_new_object_fail_when_object_could_not_be_stored() throws Exception {
        // Given
        doThrow(new StoredObjectRepositoryException(new ObjectLocation("/path_to_object/boom_object")))
                .when(storeNewObjectUseCase).execute(any());

        // When && Then
        given()
                .multiPart("content", getFileFromResource("given/fileToUpload"))
                .multiPart("fullPath", "/path_to_object/boom_object")
                .when()
                .post("/objects/upload")
                .then()
                .log().all()
                .statusCode(500);

        verify(storeNewObjectUseCase, times(1)).execute(any());
    }

    private static final class TestMetadata implements Metadata {

        @Override
        public Map<String, String> all() {
            return Map.of("header1", "value1");
        }

        @Override
        public String contentType() {
            return "image/jpeg";
        }

        @Override
        public Long contentLength() {
            return 0l;
        }
    }

    @Test
    public void should_api_get_stored_object() {
        // Given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        byteArrayOutputStream.writeBytes("content".getBytes());
        doReturn(new StoredObject(
                new TestMetadata(),
                new ObjectLocation("/path_to_object/object"),
                byteArrayOutputStream,
                new UUID(0,0).toString()
        )).when(getStoredObjectUseCase).execute(new GetStoredObjectCommand(new ObjectLocation("/path_to_object/object")));

        // When && Then
        given()
                .queryParam("objectLocation", "/path_to_object/object")
                .when()
                .get("/objects")
                .then()
                .log().all()
                .statusCode(200)
                .header("Content-Disposition", "attachment;filename=object")
                .header("Content-Type", "image/jpeg")
                .header("Content-Length", "7")
                .header("versionId", "00000000-0000-0000-0000-000000000000")
                .header("header1", "value1");

        verify(getStoredObjectUseCase, times(1)).execute(any());
    }

    @Test
    public void should_api_get_stored_object_fail_when_object_does_not_exists() throws Exception {
        // Given
        doThrow(new UnknownStoredObjectException(new ObjectLocation("/path_to_object/unknown_object")))
                .when(getStoredObjectUseCase).execute(any());

        // When && Then
        given()
                .queryParam("objectLocation", "/path_to_object/unknown_object")
                .when()
                .get("/objects")
                .then()
                .log().all()
                .statusCode(404);

        verify(getStoredObjectUseCase, times(1)).execute(any());
    }

    @Test
    public void should_api_store_new_object_fail_when_object_could_not_be_got() throws Exception {
        // Given
        doThrow(new StoredObjectRepositoryException(new ObjectLocation("/path_to_object/boom_object")))
                .when(getStoredObjectUseCase).execute(any());

        // When && Then
        given()
                .queryParam("objectLocation", "/path_to_object/boom_object")
                .when()
                .get("/objects")
                .then()
                .log().all()
                .statusCode(500);

        verify(getStoredObjectUseCase, times(1)).execute(any());
    }

    private File getFileFromResource(final String fileName) throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        return new File(resource.toURI());
    }

}
