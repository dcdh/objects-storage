package com.damdamdeo.objectsstorage.infrastructure.s3;

import com.damdamdeo.objectsstorage.domain.Metadata;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.Map;
import java.util.Objects;

public final class S3Metadata implements Metadata {

    private final Map<String, String> metadata;
    private final String contentType;
    private final Long contentLength;

    public S3Metadata(final GetObjectResponse getObjectResponse) {
        Objects.requireNonNull(getObjectResponse);
        this.metadata = Objects.requireNonNull(getObjectResponse.metadata());
        this.contentType = Objects.requireNonNull(getObjectResponse.contentType());
        this.contentLength = Objects.requireNonNull(getObjectResponse.contentLength());
    }

    @Override
    public Map<String, String> all() {
        return metadata;
    }

    @Override
    public String contentType() {
        return contentType;
    }

    @Override
    public Long contentLength() {
        return contentLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof S3Metadata)) return false;
        S3Metadata that = (S3Metadata) o;
        return Objects.equals(metadata, that.metadata) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(contentLength, that.contentLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata, contentType, contentLength);
    }
}
