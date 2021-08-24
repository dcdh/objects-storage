package com.damdamdeo.objectsstorage.domain;

import java.util.Objects;

public final class ObjectCreated {

    private final ObjectLocation objectLocation;
    private final String versionId;

    public ObjectCreated(final ObjectLocation objectLocation,
                         final String versionId) {
        this.objectLocation = Objects.requireNonNull(objectLocation);
        this.versionId = Objects.requireNonNull(versionId);
    }

    public ObjectLocation getObjectLocation() {
        return objectLocation;
    }

    public String getVersionId() {
        return versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectCreated)) return false;
        ObjectCreated that = (ObjectCreated) o;
        return Objects.equals(objectLocation, that.objectLocation) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectLocation, versionId);
    }
}
