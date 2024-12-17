package pl.slabonart.module_6;

import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.exception.ValueForPlaceholderNotProvidedException;
import pl.slabonart.module_6.sender.MessageSender;
import pl.slabonart.module_6.template.Template;
import pl.slabonart.module_6.template.TemplateEngine;

public class Messenger {

    private final MessageSender messageSender;
    private final TemplateEngine templateEngine;

    public Messenger(MessageSender messageSender,
                     TemplateEngine templateEngine) {
        this.messageSender = messageSender;
        this.templateEngine = templateEngine;
    }

    public void sendMessage(Client client, Template template) throws ValueForPlaceholderNotProvidedException {
        String messageContent = templateEngine.generateMessage(template, client);
        messageSender.send(client.getAddresses(), messageContent);
    }

}
