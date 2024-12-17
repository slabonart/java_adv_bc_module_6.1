package pl.slabonart.module_6.loader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class FileValuesLoader implements ValuesLoader {

    private static final String FILE_PATH = "src/main/resources/values/";
    private static final String FILE_EXTENSION= ".json";
    private static final String ERROR_WHILE_LOADING_VALUES_MESSAGE = "Error while loading values from file: ";

    private final String fileName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileValuesLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, String> loadValues() {
        try (InputStream inputStream = new FileInputStream(FILE_PATH + fileName + FILE_EXTENSION)){
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
            return objectMapper.readValue(reader, Map.class);
        } catch (IOException e) {
            System.out.println(ERROR_WHILE_LOADING_VALUES_MESSAGE + fileName + FILE_EXTENSION);
            return Collections.emptyMap();
        }
    }

}
