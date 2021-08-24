package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.ObjectCreated;
import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import com.damdamdeo.objectsstorage.domain.StoredObject;
import com.damdamdeo.objectsstorage.domain.usecase.GetStoredObjectCommand;
import com.damdamdeo.objectsstorage.domain.usecase.GetStoredObjectUseCase;
import com.damdamdeo.objectsstorage.domain.usecase.StoreNewObjectCommand;
import com.damdamdeo.objectsstorage.domain.usecase.StoreNewObjectUseCase;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

@Path("/objects")
public class ObjectEndpoint {

    private final GetStoredObjectUseCase getStoredObjectUseCase;
    private final StoreNewObjectUseCase storeNewObjectUseCase;

    public ObjectEndpoint(final GetStoredObjectUseCase getStoredObjectUseCase,
                          final StoreNewObjectUseCase storeNewObjectUseCase) {
        this.getStoredObjectUseCase = Objects.requireNonNull(getStoredObjectUseCase);
        this.storeNewObjectUseCase = Objects.requireNonNull(storeNewObjectUseCase);
    }

    public static class ObjectLocationDTO {
        private final String fullPath;
        private final String fileName;

        public ObjectLocationDTO(final ObjectLocation objectLocation) {
            this.fullPath = objectLocation.fullPath();
            this.fileName = objectLocation.fileName();
        }

        public String getFullPath() {
            return fullPath;
        }

        public String getFileName() {
            return fileName;
        }
    }

    public static class ObjectCreatedDTO {
        private final ObjectLocationDTO objectLocation;
        private final String versionId;

        public ObjectCreatedDTO(final ObjectCreated objectCreated) {
            this.objectLocation = new ObjectLocationDTO(objectCreated.getObjectLocation());
            this.versionId = objectCreated.getVersionId();
        }

        public ObjectLocationDTO getObjectLocation() {
            return objectLocation;
        }

        public String getVersionId() {
            return versionId;
        }
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadObject(@MultipartForm final ObjectUploaded objectUploaded) {
        final ObjectCreated objectCreated = storeNewObjectUseCase.execute(new StoreNewObjectCommand(
                objectUploaded.objectLocation(),
                objectUploaded.content
        ));
        return Response.created(null)
                .entity(new ObjectCreatedDTO(objectCreated))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response downloadObject(@QueryParam("objectLocation") final String objectLocation) {
        final StoredObject storedObject = getStoredObjectUseCase.execute(new GetStoredObjectCommand(new ObjectLocation(objectLocation)));
        // FIXME le cast vers ByteArrayOutputStream me choque !
        final Response.ResponseBuilder responseBuilder = Response.ok(new ByteArrayInputStream(((ByteArrayOutputStream) storedObject.content()).toByteArray()));
        responseBuilder.header("Content-Disposition", "attachment;filename=" + storedObject.objectKey().fileName());
        responseBuilder.header("Content-Type", storedObject.metadata().contentType());
        responseBuilder.header("Content-Length", storedObject.metadata().contentLength());
        responseBuilder.header("versionId", storedObject.versionId());
        storedObject.metadata().all().forEach((name, value) -> responseBuilder.header(name, value));
        return responseBuilder.build();
    }

}
