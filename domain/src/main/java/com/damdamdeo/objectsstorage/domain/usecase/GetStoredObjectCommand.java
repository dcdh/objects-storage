package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import com.damdamdeo.objectsstorage.domain.UseCaseCommand;

import java.util.Objects;

public final class GetStoredObjectCommand implements UseCaseCommand {

    private final ObjectLocation objectLocation;

    public GetStoredObjectCommand(final ObjectLocation objectLocation) {
        this.objectLocation = Objects.requireNonNull(objectLocation);
    }

    public ObjectLocation objectKey() {
        return objectLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetStoredObjectCommand)) return false;
        GetStoredObjectCommand that = (GetStoredObjectCommand) o;
        return Objects.equals(objectLocation, that.objectLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectLocation);
    }

}
