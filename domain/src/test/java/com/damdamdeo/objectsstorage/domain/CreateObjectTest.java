package com.damdamdeo.objectsstorage.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class CreateObjectTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(CreateObject.class).verify();
    }

    @Test
    public void should_fail_fast_when_metadata_is_null() {
        assertThatThrownBy(() -> new CreateObject(null, mock(ObjectLocation.class), "content".getBytes()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_object_key_is_null() {
        assertThatThrownBy(() -> new CreateObject(mock(Metadata.class), null, "content".getBytes()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_content_is_null() {
        assertThatThrownBy(() -> new CreateObject(mock(Metadata.class), mock(ObjectLocation.class), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_return_metadata() {
        // Given
        final Metadata givenMetadata = mock(Metadata.class);

        // When && Then
        assertThat(new CreateObject(givenMetadata, mock(ObjectLocation.class), "content".getBytes()).metadata())
                .isEqualTo(givenMetadata);
    }

    @Test
    public void should_return_object_key() {
        // Given
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);

        // When && Then
        assertThat(new CreateObject(mock(Metadata.class), givenObjectLocation, "content".getBytes()).objectKey())
                .isEqualTo(givenObjectLocation);
    }

    @Test
    public void should_return_content() {
        assertThat(new CreateObject(mock(Metadata.class), mock(ObjectLocation.class), "content".getBytes()).content())
                .isEqualTo("content".getBytes());
    }

}
