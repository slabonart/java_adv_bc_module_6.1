package pl.slabonart.module_6.sender;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileSenderTest {

    private static final String MESSAGES_PATH = "src/main/resources/messages";
    private static final String TEST_FILE_NAME = "testMessage";
    private FileSender fileSender;

    @BeforeEach
    void setUp() {
        fileSender = new FileSender(TEST_FILE_NAME);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path filePath = Paths.get(MESSAGES_PATH, TEST_FILE_NAME + ".txt");
        Files.deleteIfExists(filePath);

        Path messagesDirectory = Paths.get(MESSAGES_PATH);
        if (Files.isDirectory(messagesDirectory) && Files.list(messagesDirectory).findAny().isEmpty()) {
            Files.delete(messagesDirectory);
        }
    }

    @ParameterizedTest
    @MethodSource("getLatinStrings")
    void whenSenderWritesFileWithLatin1Encoding_thenProperContentSaved(String input) throws IOException {

        fileSender.send(TEST_FILE_NAME, input);

        Path filePath = Paths.get(MESSAGES_PATH, TEST_FILE_NAME + ".txt");
        assertTrue(Files.exists(filePath), "File should be created.");

        byte[] fileBytes = Files.readAllBytes(filePath);
        String fileContent = new String(fileBytes, StandardCharsets.ISO_8859_1);
        assertEquals(input, fileContent, "File content should match the original message.");
    }

    private static Stream<String> getLatinStrings() {
        return Stream.of(
                "^@^A^B^C^D^E^F^G^H^I^J^K^L^M^N^O^P^Q^R^S^T^U^V^W^X^Y^Z^[^\\^^^^_]",
                " ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿",
                "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ"
        );
    }
}