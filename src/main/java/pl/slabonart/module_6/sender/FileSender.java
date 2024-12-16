package pl.slabonart.module_6.sender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender implements MessageSender {

    private static final String MESSAGES_PATH = "src/main/resources/messages";
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
            Files.writeString(filePath, message);
            System.out.println("Message successfully saved to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to send the message: " + e.getMessage());
        }
    }
}
