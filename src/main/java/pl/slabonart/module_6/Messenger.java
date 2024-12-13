package pl.slabonart.module_6;

import pl.slabonart.module_6.template.Template;
import pl.slabonart.module_6.template.TemplateEngine;

public class Messenger {

    private MailServer mailServer;

    private TemplateEngine templateEngine;


    public Messenger(MailServer mailServer,
                     TemplateEngine templateEngine) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }

    public void sendMessage(Client client, Template template) {
        String messageContent =
                templateEngine.generateMessage(template, client);
        mailServer.send(client.getAddresses(), messageContent);
    }
}
