package com.damdamdeo.objectsstorage.infrastructure.s3;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class S3MetadataTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(S3Metadata.class).verify();
    }

    @Test
    public void should_fail_fast_when_get_object_response_is_null() {
        assertThatThrownBy(() -> new S3Metadata(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_metadata_is_null() {
        // Given
        final GetObjectResponse getObjectResponse = mock(GetObjectResponse.class);
        doReturn(null).when(getObjectResponse).metadata();

        // When && Then
        assertThatThrownBy(() -> new S3Metadata(getObjectResponse)).isInstanceOf(NullPointerException.class);
        verify(getObjectResponse, times(1)).metadata();
        verifyNoMoreInteractions(getObjectResponse);
    }

    @Test
    public void should_fail_fast_when_content_type_is_null() {
        // Given
        final GetObjectResponse getObjectResponse = mock(GetObjectResponse.class);
        doReturn(mock(HashMap.class)).when(getObjectResponse).metadata();

        // When && Then
        assertThatThrownBy(() -> new S3Metadata(getObjectResponse)).isInstanceOf(NullPointerException.class);
        verify(getObjectResponse, times(1)).metadata();
        verify(getObjectResponse, times(1)).contentType();
        verifyNoMoreInteractions(getObjectResponse);
    }

    @Test
    public void should_return_metadata() {
        // Given
        final GetObjectResponse getObjectResponse = mock(GetObjectResponse.class);
        final Map<String, String> givenMetadata = mock(HashMap.class);
        doReturn(givenMetadata).when(getObjectResponse).metadata();
        doReturn("contentType").when(getObjectResponse).contentType();
        doReturn(42L).when(getObjectResponse).contentLength();

        // When && Then
        assertThat(new S3Metadata(getObjectResponse).all()).isEqualTo(givenMetadata);
    }

    @Test
    public void should_return_content_type() {
        // Given
        final GetObjectResponse getObjectResponse = mock(GetObjectResponse.class);
        doReturn(mock(HashMap.class)).when(getObjectResponse).metadata();
        doReturn("contentType").when(getObjectResponse).contentType();
        doReturn(42L).when(getObjectResponse).contentLength();

        // When && Then
        assertThat(new S3Metadata(getObjectResponse).contentType()).isEqualTo("contentType");
    }

    @Test
    public void should_return_content_length() {
        // Given
        final GetObjectResponse getObjectResponse = mock(GetObjectResponse.class);
        doReturn(mock(HashMap.class)).when(getObjectResponse).metadata();
        doReturn("contentType").when(getObjectResponse).contentType();
        doReturn(42L).when(getObjectResponse).contentLength();

        // When && Then
        assertThat(new S3Metadata(getObjectResponse).contentLength()).isEqualTo(42L);
    }

}
