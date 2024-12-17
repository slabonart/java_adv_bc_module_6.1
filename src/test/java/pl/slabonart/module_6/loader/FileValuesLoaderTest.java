package pl.slabonart.module_6.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileValuesLoaderTest {

    private static final String LATIN_VALUES_FILE_NAME = "latin1-valid";
    private static final String LATIN_VALUES_FILE_NAME_FULL = LATIN_VALUES_FILE_NAME + ".json";

    @TempDir
    static Path tempDir;

    @Test
    void whenLoadValues_withLatin1Characters_thenSuccess() throws IOException {
        FileValuesLoader loader = new FileValuesLoader(LATIN_VALUES_FILE_NAME);
        loader.setFilePath(tempDir.resolve("") + "/");

        Files.writeString(tempDir.resolve(LATIN_VALUES_FILE_NAME_FULL), getTestFileContent());

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

    private String getTestFileContent() {
        return new StringBuilder()
                .append("{")
                .append("  \"key1\": \"value1\",\n")
                .append("  \"keyÉ\": \"valÜe\",\n")
                .append("  \"name\": \"Jöhn Dœ\",\n")
                .append("  \"greeting\": \"¡Hola, señor!\",\n")
                .append("  \"currency\": \"€ 100, £ 50, ¥ 30\",\n")
                .append("  \"symbols\": \"© ® ß æ ø\",\n")
                .append("  \"quote\": \"“Hello, World!”\"\n")
                .append("}").toString();
    }

}