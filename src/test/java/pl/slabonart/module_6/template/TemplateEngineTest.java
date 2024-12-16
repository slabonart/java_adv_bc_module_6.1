package pl.slabonart.module_6.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.exception.ValueForPlaceholderNotProvidedException;
import pl.slabonart.module_6.loader.ValuesLoader;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateEngineTest {

    private static final String SIMPLE_TEMPLATE_MESSAGE = "Hello #{subject} !!!";
    private static final String TWO_PLACEHOLDERS_TEMPLATE_MESSAGE = "Hello #{name}, #{subject} !!!";
    private static final Map<String, String> TEST_VALUES = Map.of("subject", "subject_value");
    private static final Map<String, String> TEST_VALUES_2 = Map.of("subject", "subject_value", "name", "name_value");
    private static final String EXPECTED_MESSAGE = "Hello subject_value !!!";
    private static final String NO_NAME_VALUE_ERROR_MESSAGE = "Value for placeholder 'name' was not provided";

    @Mock
    ValuesLoader valuesLoader;

    @InjectMocks
    ConsoleModeTemplateEngine consoleModeTemplateEngine;

    @InjectMocks
    FileModeTemplateEngine fileModeTemplateEngine;

    @Test
    void givenConsoleTemplateEngine_whenGenerateMessage_thenReturnProperMessage() throws ValueForPlaceholderNotProvidedException {

        when(valuesLoader.loadValues()).thenReturn(TEST_VALUES);

        Template template = new Template();
        template.setMessage(SIMPLE_TEMPLATE_MESSAGE);

        String message = consoleModeTemplateEngine.generateMessage(template, new Client());

        assertEquals(EXPECTED_MESSAGE, message);
    }

    @Test
    void givenFileTemplateEngine_whenGenerateMessage_thenReturnProperMessage() throws ValueForPlaceholderNotProvidedException {

        when(valuesLoader.loadValues()).thenReturn(TEST_VALUES);

        Template template = new Template();
        template.setMessage(SIMPLE_TEMPLATE_MESSAGE);

        String message = fileModeTemplateEngine.generateMessage(template, new Client());

        assertEquals(EXPECTED_MESSAGE, message);
    }

    @Test
    void givenConsoleTemplateEngine_whenGenerateMessage_andValueNotProvided_thenThrowException() {

        when(valuesLoader.loadValues()).thenReturn(TEST_VALUES);

        Template template = new Template();
        template.setMessage(TWO_PLACEHOLDERS_TEMPLATE_MESSAGE);

        Exception exception = assertThrows(Exception.class, () -> consoleModeTemplateEngine.generateMessage(template, new Client()));

        assertInstanceOf(ValueForPlaceholderNotProvidedException.class, exception);
        assertEquals(NO_NAME_VALUE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void givenFileTemplateEngine_henGenerateMessage_andValueNotProvided_thenThrowException() {

        when(valuesLoader.loadValues()).thenReturn(TEST_VALUES);

        Template template = new Template();
        template.setMessage(TWO_PLACEHOLDERS_TEMPLATE_MESSAGE);

        Exception exception = assertThrows(Exception.class, () -> fileModeTemplateEngine.generateMessage(template, new Client()));

        assertInstanceOf(ValueForPlaceholderNotProvidedException.class, exception);
        assertEquals(NO_NAME_VALUE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void givenConsoleTemplateEngine_whenGenerateMessage_andPassUnknownValue_thenReturnProperMessage() throws ValueForPlaceholderNotProvidedException {

        when(valuesLoader.loadValues()).thenReturn(TEST_VALUES_2);

        Template template = new Template();
        template.setMessage(SIMPLE_TEMPLATE_MESSAGE);

        String message = consoleModeTemplateEngine.generateMessage(template, new Client());

        assertEquals(EXPECTED_MESSAGE, message);
    }

    @Test
    void givenFileTemplateEngine_whenGenerateMessage_andPassUnknownValue_thenReturnProperMessage() throws ValueForPlaceholderNotProvidedException {

        when(valuesLoader.loadValues()).thenReturn(TEST_VALUES_2);

        Template template = new Template();
        template.setMessage(SIMPLE_TEMPLATE_MESSAGE);

        String message = fileModeTemplateEngine.generateMessage(template, new Client());

        assertEquals(EXPECTED_MESSAGE, message);
    }


}