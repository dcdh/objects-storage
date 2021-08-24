package com.damdamdeo.objectsstorage.domain;

public interface StoredObjectRepository {

    ObjectCreated store(CreateObject createObject) throws StoredObjectRepositoryException;

    StoredObject get(ObjectLocation objectLocation) throws UnknownStoredObjectException, StoredObjectRepositoryException;

}
