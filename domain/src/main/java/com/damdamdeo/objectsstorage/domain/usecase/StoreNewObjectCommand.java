package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import com.damdamdeo.objectsstorage.domain.UseCaseCommand;

import java.util.Arrays;
import java.util.Objects;

public final class StoreNewObjectCommand implements UseCaseCommand {

    private final ObjectLocation objectLocation;
    private final byte[] content;

    public StoreNewObjectCommand(final ObjectLocation objectLocation, final byte[] content) {
        this.objectLocation = Objects.requireNonNull(objectLocation);
        this.content = Objects.requireNonNull(content);
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
        if (!(o instanceof StoreNewObjectCommand)) return false;
        StoreNewObjectCommand that = (StoreNewObjectCommand) o;
        return Objects.equals(objectLocation, that.objectLocation) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectLocation, Arrays.hashCode(content));
    }
}
