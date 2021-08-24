package com.damdamdeo.objectsstorage.domain;

import java.util.Objects;

public final class ObjectLocation {

    private final String fullPath;

    public ObjectLocation(final String fullPath) {
        this.fullPath = Objects.requireNonNull(fullPath);
        if (fullPath.isEmpty() || !fullPath.matches("\\/[0-9a-zA-Z\\/\\.\\-\\_]+")) {
            throw new ObjectFullPathFormatInvalidException(fullPath);
        }
    }

    public String fullPath() {
        return fullPath;
    }

    public String fileName() {
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectLocation)) return false;
        ObjectLocation that = (ObjectLocation) o;
        return Objects.equals(fullPath, that.fullPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullPath);
    }
}
