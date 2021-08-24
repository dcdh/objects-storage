package com.damdamdeo.objectsstorage.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class StoredObjectTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(StoredObject.class).verify();
    }

    @Test
    public void should_fail_fast_when_metadata_is_null() {
        assertThatThrownBy(() -> new StoredObject(null, mock(ObjectLocation.class), mock(OutputStream.class), "version"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_object_key_is_null() {
        assertThatThrownBy(() -> new StoredObject(mock(Metadata.class), null, mock(OutputStream.class), "version"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_content_is_null() {
        assertThatThrownBy(() -> new StoredObject(mock(Metadata.class), mock(ObjectLocation.class), null, "version"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_version_id_is_null() {
        assertThatThrownBy(() -> new StoredObject(mock(Metadata.class), mock(ObjectLocation.class), mock(OutputStream.class), null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * assertThatThrownBy(() -> new StoredObject(mock(Metadata.class), mock(ObjectKey.class), mock(OutputStream.class), "version"))
     *                 .isInstanceOf(NullPointerException.class);
     */
    @Test
    public void should_return_metadata() {
        // Given
        final Metadata givenMetadata = mock(Metadata.class);

        // When && Then
        assertThat(new StoredObject(givenMetadata, mock(ObjectLocation.class), mock(OutputStream.class), "version").metadata())
                .isEqualTo(givenMetadata);
    }

    @Test
    public void should_return_object_key() {
        // Given
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);

        // When && Then
        assertThat(new StoredObject(mock(Metadata.class), givenObjectLocation, mock(OutputStream.class), "version").objectKey())
                .isEqualTo(givenObjectLocation);
    }

    @Test
    public void should_return_content() {
        // Given
        final OutputStream givenContent = mock(OutputStream.class);

        // When && Then
        assertThat(new StoredObject(mock(Metadata.class), mock(ObjectLocation.class), givenContent, "version").content())
                .isEqualTo(givenContent);
    }

    @Test
    public void should_return_version_id() {
        assertThat(new StoredObject(mock(Metadata.class), mock(ObjectLocation.class), mock(OutputStream.class), "version").versionId())
                .isEqualTo("version");
    }

}
