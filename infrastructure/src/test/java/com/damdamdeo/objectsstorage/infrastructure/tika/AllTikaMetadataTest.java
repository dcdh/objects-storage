package com.damdamdeo.objectsstorage.infrastructure.tika;

import io.quarkus.tika.TikaMetadata;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AllTikaMetadataTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(AllTikaMetadata.class).verify();
    }

    @Test
    public void should_fail_fast_when_tika_metadata_is_null() {
        assertThatThrownBy(() -> new AllTikaMetadata(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_return_content_type() {
        assertThat(new AllTikaMetadata(new TikaMetadata(Map.of("Content-Type", Arrays.asList("image/jpeg"))))
                .contentType())
                .isEqualTo("image/jpeg");
    }

    @Test
    public void should_return_null_content_type() {
        assertThat(new AllTikaMetadata(new TikaMetadata(new HashMap<>())).contentType()).isNull();
    }

    @Test
    public void should_return_content_length() {
        assertThat(new AllTikaMetadata(new TikaMetadata(Map.of("Content-Length", Arrays.asList("42"))))
                .contentLength())
                .isEqualTo(42L);
    }

    @Test
    public void should_return_null_content_length() {
        assertThat(new AllTikaMetadata(new TikaMetadata(new HashMap<>())).contentLength()).isNull();
    }

}
