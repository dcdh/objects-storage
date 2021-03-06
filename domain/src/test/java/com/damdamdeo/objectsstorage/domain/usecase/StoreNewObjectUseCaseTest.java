package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class StoreNewObjectUseCaseTest {

    @Test
    public void should_fail_fast_when_metadata_extractor_is_null() {
        assertThatThrownBy(() -> new StoreNewObjectUseCase(null, mock(StoredObjectRepository.class)))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_stored_object_repository_is_null() {
        assertThatThrownBy(() -> new StoreNewObjectUseCase(mock(MetadataExtractor.class), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_store_new_object() {
        // Given
        final MetadataExtractor givenMetadataExtractor = mock(MetadataExtractor.class);
        final StoredObjectRepository givenStoredObjectRepository = mock(StoredObjectRepository.class);
        final StoreNewObjectUseCase givenStoreNewObjectUseCase = new StoreNewObjectUseCase(givenMetadataExtractor, givenStoredObjectRepository);
        final ObjectCreated givenObjectCreated = mock(ObjectCreated.class);
        final Metadata givenMetadata = mock(Metadata.class);
        doReturn(givenMetadata).when(givenMetadataExtractor).extract("content".getBytes());
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);
        doReturn(givenObjectCreated).when(givenStoredObjectRepository).store(new CreateObject(givenMetadata, givenObjectLocation, "content".getBytes()));
        final InOrder inOrder = inOrder(givenMetadataExtractor, givenStoredObjectRepository);

        // When && Then
        assertThat(givenStoreNewObjectUseCase.execute(new StoreNewObjectCommand(givenObjectLocation, "content".getBytes())))
                .isEqualTo(givenObjectCreated);
        inOrder.verify(givenMetadataExtractor, times(1)).extract(any());
        inOrder.verify(givenStoredObjectRepository, times(1)).store(any());
    }

    @Test
    public void should_throw_unable_to_extract_metadata_exception_when_object_cannot_be_parsed() {
        // Given
        final MetadataExtractor givenMetadataExtractor = mock(MetadataExtractor.class);
        final StoredObjectRepository givenStoredObjectRepository = mock(StoredObjectRepository.class);
        final StoreNewObjectUseCase givenStoreNewObjectUseCase = new StoreNewObjectUseCase(givenMetadataExtractor, givenStoredObjectRepository);
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);
        doThrow(new UnableToExtractMetadataException()).when(givenMetadataExtractor).extract("content".getBytes());

        // When && Then
        assertThatThrownBy(() -> givenStoreNewObjectUseCase.execute(new StoreNewObjectCommand(givenObjectLocation, "content".getBytes())))
                .isInstanceOf(UnableToExtractMetadataException.class);
        verify(givenMetadataExtractor, times(1)).extract(any());
    }

    @Test
    public void should_throw_stored_object_repository_exception_when_an_exception_occurs_from_the_object_repository() {
        // Given
        final MetadataExtractor givenMetadataExtractor = mock(MetadataExtractor.class);
        final StoredObjectRepository givenStoredObjectRepository = mock(StoredObjectRepository.class);
        final StoreNewObjectUseCase givenStoreNewObjectUseCase = new StoreNewObjectUseCase(givenMetadataExtractor, givenStoredObjectRepository);
        final ObjectCreated givenObjectCreated = mock(ObjectCreated.class);
        final Metadata givenMetadata = mock(Metadata.class);
        doReturn(givenMetadata).when(givenMetadataExtractor).extract("content".getBytes());
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);
        doThrow(new StoredObjectRepositoryException(givenObjectLocation)).when(givenStoredObjectRepository).store(new CreateObject(givenMetadata, givenObjectLocation, "content".getBytes()));

        // When && Then
        assertThatThrownBy(() -> givenStoreNewObjectUseCase.execute(new StoreNewObjectCommand(givenObjectLocation, "content".getBytes())))
                .isInstanceOf(StoredObjectRepositoryException.class)
                .hasFieldOrPropertyWithValue("objectLocation", givenObjectLocation);
        verify(givenMetadataExtractor, times(1)).extract(any());
        verify(givenStoredObjectRepository, times(1)).store(any());
    }

}
