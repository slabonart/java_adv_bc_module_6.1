package pl.slabonart.module_6.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateTest {

    private static final String MOCK_MESSAGE = "mock_message";
    private static final String REAL_MESSAGE = "real_message";

    @Spy
    Template template;

    @Test
    void examplePartialMockingTest() {

        when(template.getMessage()).thenReturn(MOCK_MESSAGE);

        template.setMessage(REAL_MESSAGE);

        assertEquals(MOCK_MESSAGE, template.getMessage());
    }

}