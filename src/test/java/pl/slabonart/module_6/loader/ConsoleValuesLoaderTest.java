package pl.slabonart.module_6.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleValuesLoaderTest {

    private ConsoleValuesLoader consoleValuesLoader = new ConsoleValuesLoader();

    @Mock
    private Scanner mockScanner;

    @Test
    void testLoadValues() {
        // Arrange
        when(mockScanner.nextLine())
                .thenReturn("key1")
                .thenReturn("value1")
                .thenReturn("yes")
                .thenReturn("key2")
                .thenReturn("value2")
                .thenReturn("no");

        System.setIn(new java.io.ByteArrayInputStream("key1\nvalue1\nyes\nkey2\nvalue2\nno\n".getBytes()));
        consoleValuesLoader.setScanner(mockScanner);

        Map<String, String> result = consoleValuesLoader.loadValues();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }
}