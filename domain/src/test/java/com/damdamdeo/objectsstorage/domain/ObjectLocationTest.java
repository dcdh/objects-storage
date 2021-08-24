package com.damdamdeo.objectsstorage.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ObjectLocationTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(ObjectLocation.class).verify();
    }

    @Test
    public void should_fail_fast_when_full_path_is_null() {
        assertThatThrownBy(() -> new ObjectLocation(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_full_path_does_not_start_by_slash() {
        assertThatThrownBy(() -> new ObjectLocation("image.jpg")).isInstanceOf(ObjectFullPathFormatInvalidException.class);
    }

    @Test
    public void should_create_object_location() {
        assertThat(new ObjectLocation("/azertyuiopqsdfgjklmwxcvbn0123456789./-"));
    }

    @Test
    public void should_return_full_path() {
        assertThat(new ObjectLocation("/fullPath").fullPath()).isEqualTo("/fullPath");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/fullPath ", "/fullPath\\", "/fullPath*", "/fullPath?", "/fullPath:", "/fullPath;", "/fullPath!",
            "/fullPathù", "/fullPath^", "/fullPath$",
            "/fullPath+", "/fullPath=", "/fullPath}", "/fullPath{", "/fullPath'", "/fullPath\"", "/fullPathé", "/fullPath~",
            "/fullPath&", "/fullPathè", "/fullPath`", "/fullPath@", "/fullPathç"})
    public void should_throw_key_format_invalid_exception_on_unsupported_characters(final String givenFullPath) {
        assertThatThrownBy(() -> new ObjectLocation(givenFullPath))
                .isInstanceOf(ObjectFullPathFormatInvalidException.class);
    }

    @ParameterizedTest
    @CsvSource({"/root/image.jpg,image.jpg", "/image.jpg,image.jpg"})
    public void should_return_expected_file_name(final String givenFullPath, final String expectedFileName) {
        assertThat(new ObjectLocation(givenFullPath).fileName())
                .isEqualTo(expectedFileName);
    }

}
