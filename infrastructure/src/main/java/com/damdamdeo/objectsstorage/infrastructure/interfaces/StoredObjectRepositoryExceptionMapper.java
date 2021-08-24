package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.StoredObjectRepositoryException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StoredObjectRepositoryExceptionMapper implements ExceptionMapper<StoredObjectRepositoryException> {

    @Override
    public Response toResponse(final StoredObjectRepositoryException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
