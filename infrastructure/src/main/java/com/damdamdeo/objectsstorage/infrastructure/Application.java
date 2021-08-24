package com.damdamdeo.objectsstorage.infrastructure;

import com.damdamdeo.objectsstorage.domain.MetadataExtractor;
import com.damdamdeo.objectsstorage.domain.StoredObjectRepository;
import com.damdamdeo.objectsstorage.domain.usecase.GetStoredObjectUseCase;
import com.damdamdeo.objectsstorage.domain.usecase.StoreNewObjectUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class Application {

    @Produces
    @ApplicationScoped
    public StoreNewObjectUseCase storeNewObjectUseCaseProducer(final MetadataExtractor metadataExtractor,
                                                               final StoredObjectRepository storedObjectRepository) {
        return new StoreNewObjectUseCase(metadataExtractor, storedObjectRepository);
    }

    @Produces
    @ApplicationScoped
    public GetStoredObjectUseCase getStoredObjectUseCaseProducer(final StoredObjectRepository storedObjectRepository) {
        return new GetStoredObjectUseCase(storedObjectRepository);
    }

}
