package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.UnknownStoredObjectException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnknownStoredObjectExceptionMapper implements ExceptionMapper<UnknownStoredObjectException> {

    @Override
    public Response toResponse(final UnknownStoredObjectException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
