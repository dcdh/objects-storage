package com.damdamdeo.objectsstorage.infrastructure.tika;

import com.damdamdeo.objectsstorage.domain.Metadata;
import com.damdamdeo.objectsstorage.domain.MetadataExtractor;
import com.damdamdeo.objectsstorage.domain.UnableToExtractMetadataException;
import io.quarkus.tika.TikaParseException;
import io.quarkus.tika.TikaParser;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@ApplicationScoped
public class TikaMetadataExtractor implements MetadataExtractor {

    private final TikaParser tikaParser;

    public TikaMetadataExtractor(final TikaParser tikaParser) {
        this.tikaParser = Objects.requireNonNull(tikaParser);
    }

    @Override
    public Metadata extract(final byte[] content) throws UnableToExtractMetadataException {
        final InputStream inputStream = new ByteArrayInputStream(content);
        try {
            return new AllTikaMetadata(this.tikaParser.parse(inputStream).getMetadata());
        } catch (final TikaParseException tikaParseException) {
            tikaParseException.printStackTrace();
            throw new UnableToExtractMetadataException();
        }
    }

}
