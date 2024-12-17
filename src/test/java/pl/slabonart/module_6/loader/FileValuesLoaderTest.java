package pl.slabonart.module_6.loader;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileValuesLoaderTest {

    @Test
    void whenLoadValues_withLatin1Characters_thenSuccess() {
        FileValuesLoader loader = new FileValuesLoader("latin1-valid");

        Map<String, String> values = loader.loadValues();

        assertFalse(values.isEmpty());
        assertTrue(values.keySet().stream().allMatch(key -> key.matches("[\\u0000-\\u00FF]*")));
        assertTrue(values.values().stream().allMatch(value -> value.matches("[\\u0000-\\u00FF]*")));
    }

    @Test
    void whenLoadValuesFromNotExistingFile_thenReturnEmptyMap() {
        FileValuesLoader loader = new FileValuesLoader("not_existing_file");

        Map<String, String> values = loader.loadValues();

        assertTrue(values.isEmpty());
    }

}