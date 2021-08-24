package com.damdamdeo.objectsstorage.domain;

import java.util.Objects;

public class UnknownStoredObjectException extends RuntimeException {

    private final ObjectLocation objectLocation;

    public UnknownStoredObjectException(final ObjectLocation objectLocation) {
        this.objectLocation = Objects.requireNonNull(objectLocation);
    }

    public ObjectLocation objectKey() {
        return objectLocation;
    }
}
