package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.StoredObject;
import com.damdamdeo.objectsstorage.domain.ObjectLocation;
import com.damdamdeo.objectsstorage.domain.StoredObjectRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class GetStoredObjectUseCaseTest {

    @Test
    public void should_fail_fast_when_stored_object_repository_is_null() {
        assertThatThrownBy(() -> new GetStoredObjectUseCase(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_get_from_repository() {
        // Given
        final StoredObjectRepository givenStoredObjectRepository = mock(StoredObjectRepository.class);
        final GetStoredObjectUseCase givenGetStoredObjectUseCase = new GetStoredObjectUseCase(givenStoredObjectRepository);
        final StoredObject givenStoredObject = mock(StoredObject.class);
        final ObjectLocation givenObjectLocation = mock(ObjectLocation.class);
        doReturn(givenStoredObject).when(givenStoredObjectRepository).get(givenObjectLocation);

        // When && Then
        assertThat(givenGetStoredObjectUseCase.execute(new GetStoredObjectCommand(givenObjectLocation)))
                .isEqualTo(givenStoredObject);
        verify(givenStoredObjectRepository, times(1)).get(any());
    }

}
