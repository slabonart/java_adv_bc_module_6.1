package pl.slabonart.module_6.sender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileSenderTest {

    @TempDir
    static Path tempDir;

    @ParameterizedTest
    @CsvSource({
            "file1, '^@^A^B^C^D^E^F^G^H^I^J^K^L^M^N^O^P^Q^R^S^T^U^V^W^X^Y^Z^[^\\^^^^_]'",
            "file2, ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿'",
            "file3, 'ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ'"
    })
    void whenSaveFileWithLatinContent_thenSuccess(String fileName, String fileContent) throws IOException {

        FileSender fileSender = new FileSender(fileName);
        fileSender.setMessagesPath(tempDir.resolve("") + "/");

        fileSender.send(fileName, fileContent);

        Path filePath = tempDir.resolve(fileName + ".txt");

        assertTrue(Files.exists(filePath), "File should be created.");

        String content = readFileContent(filePath);
        assertEquals(content, fileContent, "File content should match the original message.");
    }

    @Test
    void whenMessagePathDoesNotExits_thenCreateItAndSSaveFile() throws IOException {
        FileSender fileSender = new FileSender("test_file");
        fileSender.setMessagesPath(tempDir.resolve("") + "/test/");

        fileSender.send("test_file", "fileContent");

        Path filePath = tempDir.resolve( "test/test_file.txt");
        assertTrue(Files.exists(filePath), "File should be created.");

        String content = readFileContent(filePath);
        assertEquals("fileContent", content,  "File content should match the original message.");
    }

    private String readFileContent(Path filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        return new String(fileBytes, StandardCharsets.ISO_8859_1);
    }

}
