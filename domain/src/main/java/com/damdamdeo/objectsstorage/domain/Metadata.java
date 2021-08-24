package com.damdamdeo.objectsstorage.domain;

import java.util.Map;

public interface Metadata {

    Map<String, String> all();

    String contentType();

    Long contentLength();

}
