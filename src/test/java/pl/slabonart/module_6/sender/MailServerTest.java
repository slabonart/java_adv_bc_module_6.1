package pl.slabonart.module_6.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import pl.slabonart.module_6.OnDemandTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;


class MailServerTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private MailServer mailServer;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        mailServer = new MailServer();
    }

    @OnDemandTest
    @EnabledIfSystemProperty(named = "os.name", matches = ".*Windows.*")
    void whenSendMessage_andWindowsOS_thenWriteConsoleMessage() {

        runTest();
    }

    @OnDemandTest
    @EnabledIfSystemProperty(named = "os.name", matches = ".*Linux.*")
    void whenSendMessage_andLinuxOS_thenWriteConsoleMessage() {

        runTest();
    }

    @OnDemandTest
    @EnabledIfSystemProperty(named = "os.name", matches = ".*Mac.*")
    void whenSendMessage_andMacOS_thenWriteConsoleMessage() {

        runTest();
    }

    private void runTest() {
        String destination = "user@example.com";
        String message = "Hello, this is a test message.";

        mailServer.send(destination, message);

        String consoleOutput = outputStreamCaptor.toString().trim();

        assertTrue(consoleOutput.contains("Sending email to " + destination),
                "The output should contain the correct destination.");
        assertTrue(consoleOutput.contains("Message:"),
                "The output should contain the message label.");
        assertTrue(consoleOutput.contains(message),
                "The output should contain the correct message content.");
    }
}