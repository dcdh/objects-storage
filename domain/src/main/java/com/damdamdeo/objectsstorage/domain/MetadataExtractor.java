package com.damdamdeo.objectsstorage.domain;

public interface MetadataExtractor {

    Metadata extract(byte[] content) throws UnableToExtractMetadataException;

}
