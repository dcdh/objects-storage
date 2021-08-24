package com.damdamdeo.objectsstorage.domain;

import java.util.Objects;

public class StoredObjectRepositoryException extends RuntimeException {

    private final ObjectLocation objectLocation;

    public StoredObjectRepositoryException(final ObjectLocation objectLocation) {
        this.objectLocation = Objects.requireNonNull(objectLocation);
    }

    public ObjectLocation objectKey() {
        return objectLocation;
    }
}
