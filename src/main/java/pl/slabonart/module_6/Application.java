package pl.slabonart.module_6;

import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.exception.ValueForPlaceholderNotProvidedException;
import pl.slabonart.module_6.loader.ConsoleValuesLoader;
import pl.slabonart.module_6.loader.FileValuesLoader;
import pl.slabonart.module_6.sender.FileSender;
import pl.slabonart.module_6.sender.MailServer;
import pl.slabonart.module_6.template.ConsoleModeTemplateEngine;
import pl.slabonart.module_6.template.FileModeTemplateEngine;
import pl.slabonart.module_6.template.Template;

public class Application {

    public static void main(String[] args) {

        Messenger messenger;

        if (args.length == 0) {
            messenger = new Messenger(new MailServer(), new ConsoleModeTemplateEngine(new ConsoleValuesLoader()));
        } else if (args.length == 2) {
            messenger = new Messenger(new FileSender(args[1]), new FileModeTemplateEngine(new FileValuesLoader(args[0])));
        } else {
            System.out.println("Error starting application - wrong arguments count: " + args.length);
            return;
        }

        Template template = new Template();
        template.setMessage("Hello world and #{message} !!!");

        Client client = new Client("default@gmail.com");

        try {
            messenger.sendMessage(client, template);
        } catch (ValueForPlaceholderNotProvidedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
