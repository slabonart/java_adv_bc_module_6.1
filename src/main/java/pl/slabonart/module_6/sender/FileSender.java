package pl.slabonart.module_6.sender;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender implements MessageSender {

    private static final String MESSAGES_PATH = "src/main/resources/messages";
    private static final String MESSAGE_SUCCESSFULLY_SAVED = "Message successfully saved to: ";
    private static final String FAILED_TO_SEND_THE_MESSAGE = "Failed to send the message: ";
    private final String messageFilename;

    public FileSender(String messageFilename) {
        this.messageFilename = messageFilename;
    }

    @Override
    public void send(String destination, String message) {

        Path messagesDirectory = Paths.get(MESSAGES_PATH);
        try {
            if (!Files.exists(messagesDirectory)) {
                Files.createDirectories(messagesDirectory);
            }
            Path filePath = messagesDirectory.resolve(messageFilename + ".txt");
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.ISO_8859_1)) {
                writer.write(message);
            }
            System.out.println(MESSAGE_SUCCESSFULLY_SAVED + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println(FAILED_TO_SEND_THE_MESSAGE + e.getMessage());
        }
    }
}
