package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.*;

import java.util.Objects;

public class GetStoredObjectUseCase implements UseCase<GetStoredObjectCommand, StoredObject> {

    private final StoredObjectRepository storedObjectRepository;

    public GetStoredObjectUseCase(final StoredObjectRepository storedObjectRepository) {
        this.storedObjectRepository = Objects.requireNonNull(storedObjectRepository);
    }

    @Override
    public StoredObject execute(final GetStoredObjectCommand command)
            throws UnknownStoredObjectException, StoredObjectRepositoryException {
        return storedObjectRepository.get(command.objectKey());
    }

}
