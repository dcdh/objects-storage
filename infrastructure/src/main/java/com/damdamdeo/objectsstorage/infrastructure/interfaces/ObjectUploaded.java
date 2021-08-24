package com.damdamdeo.objectsstorage.infrastructure.interfaces;

import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class ObjectUploaded {

    @FormParam("content")
    @Schema(type = SchemaType.STRING, format = "binary", description = "content data")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] content;

    @FormParam("fullPath")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public String fullPath;

    public ObjectLocation objectLocation() {
        return new ObjectLocation(fullPath);
    }

}
