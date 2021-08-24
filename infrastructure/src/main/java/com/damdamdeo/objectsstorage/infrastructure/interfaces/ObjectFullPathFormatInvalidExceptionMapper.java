package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.ObjectFullPathFormatInvalidException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectFullPathFormatInvalidExceptionMapper implements ExceptionMapper<ObjectFullPathFormatInvalidException> {

    @Override
    public Response toResponse(final ObjectFullPathFormatInvalidException exception) {
        exception.printStackTrace();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
