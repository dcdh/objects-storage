package com.damdamdeo.objectsstorage.domain;

import java.io.OutputStream;
import java.util.Objects;

public final class StoredObject {

    private final Metadata metadata;
    private final ObjectLocation objectLocation;
    private final OutputStream content;
    private final String versionId;

    public StoredObject(final Metadata metadata,
                        final ObjectLocation objectLocation,
                        final OutputStream content,
                        final String versionId) {
        this.metadata = Objects.requireNonNull(metadata);
        this.objectLocation = Objects.requireNonNull(objectLocation);
        this.content = Objects.requireNonNull(content);
        this.versionId = Objects.requireNonNull(versionId);
    }

    public Metadata metadata() {
        return metadata;
    }

    public ObjectLocation objectKey() {
        return objectLocation;
    }

    public OutputStream content() {
        return content;
    }

    public String versionId() {
        return versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoredObject)) return false;
        StoredObject that = (StoredObject) o;
        return Objects.equals(metadata, that.metadata) &&
                Objects.equals(objectLocation, that.objectLocation) &&
                Objects.equals(content, that.content) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata, objectLocation, content, versionId);
    }
}
