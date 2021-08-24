package com.damdamdeo.objectsstorage.domain;

import java.util.Objects;

public class ObjectFullPathFormatInvalidException extends RuntimeException {

    private final String key;

    public ObjectFullPathFormatInvalidException(final String key) {
        this.key = Objects.requireNonNull(key);
    }

    public String key() {
        return key;
    }
}
