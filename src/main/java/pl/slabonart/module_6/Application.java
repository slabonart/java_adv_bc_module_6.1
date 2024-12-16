package pl.slabonart.module_6;

import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.sender.FileSender;
import pl.slabonart.module_6.sender.MailServer;
import pl.slabonart.module_6.template.ConsoleModeTemplateEngine;
import pl.slabonart.module_6.template.FileModeTemplateEngine;
import pl.slabonart.module_6.template.Template;

public class Application {

    public static void main(String[] args) {

        Messenger messenger;

        if (args.length == 0) {
            messenger = new Messenger(new MailServer(), new ConsoleModeTemplateEngine());
        } else if (args.length == 2) {
            messenger = new Messenger(new FileSender(args[1]), new FileModeTemplateEngine(args[0]));
        } else {
            System.out.println("Error starting application - wrong arguments count: " + args.length);
            return;
        }

        Template template = new Template();
        template.setMessage("Hello world and #{message} !!!");

        Client client = new Client();
        client.setAddresses("default@gmail.com");

        messenger.sendMessage(client, template);
    }
}
