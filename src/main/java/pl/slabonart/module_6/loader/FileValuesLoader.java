package pl.slabonart.module_6.loader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class FileValuesLoader implements ValuesLoader {

    private static final String FILE_PATH = "src/main/resources/values/";
    private static final String FILE_EXTENSION= ".json";

    private final String fileName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileValuesLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, String> loadValues() {
        try {
            return objectMapper.readValue(new File(FILE_PATH + fileName + FILE_EXTENSION), Map.class);
        } catch (IOException e) {
            System.out.println("Error while loading values from file: " + fileName + FILE_EXTENSION);
            return Collections.emptyMap();
        }
    }

}
