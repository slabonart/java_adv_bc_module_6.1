package pl.slabonart.module_6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.exception.ValueForPlaceholderNotProvidedException;
import pl.slabonart.module_6.sender.MessageSender;
import pl.slabonart.module_6.template.Template;
import pl.slabonart.module_6.template.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessengerTest {

    private static final String ERROR_MESSAGE = "Value for placeholder 'message' was not provided";
    private static final String TEST_MESSAGE = "test message";

    @Spy
    MessageSender messageSender;

    @Mock
    TemplateEngine templateEngine;

    @InjectMocks
    Messenger messenger;

    @Test
    void whenSendMessage_andMessageGenerated_thenCallSenderMethod() throws ValueForPlaceholderNotProvidedException {

        when(templateEngine.generateMessage(any(), any())).thenReturn(TEST_MESSAGE);

        messenger.sendMessage(new Client(), new Template());

        verify(messageSender, times(1)).send(any(), any());
    }

    @Test
    void whenSendMessage_andExceptionIsThrown_thenDoNotCallSenderMethod() throws ValueForPlaceholderNotProvidedException {

        when(templateEngine.generateMessage(any(), any())).thenThrow(new ValueForPlaceholderNotProvidedException("message"));

        Exception exception = assertThrows(Exception.class, () ->  messenger.sendMessage(new Client(), new Template()));

        assertInstanceOf(ValueForPlaceholderNotProvidedException.class, exception);
        assertEquals(ERROR_MESSAGE, exception.getMessage());

        verify(messageSender, never()).send(any(), any());
    }

}
