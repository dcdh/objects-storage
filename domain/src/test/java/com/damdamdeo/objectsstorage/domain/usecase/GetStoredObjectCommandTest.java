package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class GetStoredObjectCommandTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(GetStoredObjectCommand.class).verify();
    }

    @Test
    public void should_fail_fast_when_object_key_is_null() {
        assertThatThrownBy(() -> new GetStoredObjectCommand(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_create_get_stored_object_command() {
        assertThat(new GetStoredObjectCommand(mock(ObjectLocation.class)));
    }

    @Test
    public void should_return_object_key() {
        // Given
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);
        final GetStoredObjectCommand givenGetStoredObjectCommand = new GetStoredObjectCommand(givenObjectLocation);

        // When && Then
        assertThat(givenGetStoredObjectCommand.objectKey()).isEqualTo(givenObjectLocation);
    }

}
