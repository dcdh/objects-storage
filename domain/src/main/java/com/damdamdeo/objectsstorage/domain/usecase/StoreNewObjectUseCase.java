package com.damdamdeo.objectsstorage.domain.usecase;

import com.damdamdeo.objectsstorage.domain.*;

import java.util.Objects;

public class StoreNewObjectUseCase implements UseCase<StoreNewObjectCommand, ObjectCreated> {

    private final MetadataExtractor metadataExtractor;
    private final StoredObjectRepository storedObjectRepository;

    public StoreNewObjectUseCase(final MetadataExtractor metadataExtractor,
                                 final StoredObjectRepository storedObjectRepository) {
        this.metadataExtractor = Objects.requireNonNull(metadataExtractor);
        this.storedObjectRepository = Objects.requireNonNull(storedObjectRepository);
    }

    @Override
    public ObjectCreated execute(final StoreNewObjectCommand command)
            throws UnableToExtractMetadataException, StoredObjectRepositoryException {
        final Metadata metadata = metadataExtractor.extract(command.content());
        return storedObjectRepository
                .store(new CreateObject(metadata, command.objectKey(), command.content()));
    }

}
