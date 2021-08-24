package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.UnableToExtractMetadataException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToExtractMetadataExceptionMapper implements ExceptionMapper<UnableToExtractMetadataException> {

    @Override
    public Response toResponse(final UnableToExtractMetadataException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
