package com.damdamdeo.objectsstorage.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class ObjectCreatedTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(ObjectCreated.class).verify();
    }

    @Test
    public void should_fail_fast_when_object_key_is_null() {
        assertThatThrownBy(() -> new ObjectCreated(null, "version"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_version_null() {
        assertThatThrownBy(() -> new ObjectCreated(mock(ObjectLocation.class), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_return_object_key() {
        // Given
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);

        // When && Then
        assertThat(new ObjectCreated(givenObjectLocation, "version").getObjectLocation())
                .isEqualTo(givenObjectLocation);
    }

    @Test
    public void should_return_version() {
        assertThat(new ObjectCreated(mock(ObjectLocation.class), "version").getVersionId())
                .isEqualTo("version");
    }

}
