package com.damdamdeo.objectsstorage.infrastructure.tika;

import com.damdamdeo.objectsstorage.domain.Metadata;
import io.quarkus.tika.TikaMetadata;

import java.util.*;
import java.util.stream.Collectors;

public final class AllTikaMetadata implements Metadata {

    private final Map<String, String> metadata;
    private final String contentType;
    private final Long contentLength;

    public AllTikaMetadata(final TikaMetadata tikaMetadata) {
        final Set<String> metadataNames = Objects.requireNonNull(tikaMetadata).getNames();
        this.metadata = new HashMap<>(metadataNames.size());
        metadataNames.stream()
                .forEach(metadataName ->
                        this.metadata.put(metadataName,
                                tikaMetadata
                                        .getValues(metadataName)
                                        .stream().collect(Collectors.joining(","))));
        this.contentType = this.metadata.get("Content-Type");
        this.contentLength = Optional.ofNullable(this.metadata.get("Content-Length"))
                .map(Long::valueOf)
                .orElse(null);
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
        if (!(o instanceof AllTikaMetadata)) return false;
        AllTikaMetadata that = (AllTikaMetadata) o;
        return Objects.equals(metadata, that.metadata) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(contentLength, that.contentLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata, contentType, contentLength);
    }

}
