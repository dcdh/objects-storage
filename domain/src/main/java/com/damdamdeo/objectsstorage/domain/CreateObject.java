package com.damdamdeo.objectsstorage.domain;

import java.util.Arrays;
import java.util.Objects;

public final class CreateObject {

    private final Metadata metadata;
    private final ObjectLocation objectLocation;
    private final byte[] content;

    public CreateObject(final Metadata metadata,
                        final ObjectLocation objectLocation,
                        final byte[] content) {
        this.metadata = Objects.requireNonNull(metadata);
        this.objectLocation = Objects.requireNonNull(objectLocation);
        this.content = Objects.requireNonNull(content);
    }

    public Metadata metadata() {
        return metadata;
    }

    public ObjectLocation objectKey() {
        return objectLocation;
    }

    public byte[] content() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateObject)) return false;
        CreateObject that = (CreateObject) o;
        return Objects.equals(metadata, that.metadata) &&
                Objects.equals(objectLocation, that.objectLocation) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(metadata, objectLocation);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
