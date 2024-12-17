package pl.slabonart.module_6;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Optional;

public class TestExecutionLoggerExtension implements TestWatcher, BeforeTestExecutionCallback {

    private static final Path LOG_FILE_PATH = Paths.get("src/test/resources/test_results", "test-execution-log.txt");
    private static final ThreadLocal<LocalDateTime> startTime = new ThreadLocal<>();

    static {
        try {
            Files.createDirectories(LOG_FILE_PATH.getParent());
            if (!Files.exists(LOG_FILE_PATH)) {
                Files.createFile(LOG_FILE_PATH);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize log file: " + e.getMessage());
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        startTime.set(LocalDateTime.now());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        writeTestExecutionInfo(context, "SUCCESS");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        writeTestExecutionInfo(context, "FAILED: " + cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        writeTestExecutionInfo(context, "ABORTED: " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        writeTestExecutionInfo(context, "DISABLED: " + reason.orElse("No reason"));
    }

    private void writeTestExecutionInfo(ExtensionContext context, String status) {
        String testName = context.getDisplayName();
        String startTimeInfo = startTime.get().toString();
        String endTimeInfo = LocalDateTime.now().toString();

        String logMessage = String.format("Test: %s | Status: %s | Started: %s | Ended: %s%n",
                testName, status, startTimeInfo, endTimeInfo);

        try (BufferedWriter writer = Files.newBufferedWriter(LOG_FILE_PATH, StandardOpenOption.APPEND)) {
            writer.write(logMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to log test execution info: " + e.getMessage());
        }
    }
}