package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class StoreNewObjectCommandTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(StoreNewObjectCommand.class).verify();
    }

    @Test
    public void should_fail_fast_when_object_key_is_null() {
        assertThatThrownBy(() -> new StoreNewObjectCommand(null, "content".getBytes()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_content_is_null() {
        assertThatThrownBy(() -> new StoreNewObjectCommand(mock(ObjectLocation.class), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_return_object_key() {
        // Given
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);
        final StoreNewObjectCommand givenStoreNewObjectCommand = new StoreNewObjectCommand(givenObjectLocation, "content".getBytes());

        // When && Then
        assertThat(givenStoreNewObjectCommand.objectKey()).isEqualTo(givenObjectLocation);
    }

    @Test
    public void should_return_content() {
        // Given
        final StoreNewObjectCommand givenStoreNewObjectCommand = new StoreNewObjectCommand(mock(ObjectLocation.class), "content".getBytes());

        // When && Then
        assertThat(givenStoreNewObjectCommand.content()).isEqualTo("content".getBytes());
    }

}
