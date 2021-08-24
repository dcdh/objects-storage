package com.damdamdeo.objectsstorage.domain;

public interface UseCase<C extends UseCaseCommand, O> {

    O execute(C command);

}
