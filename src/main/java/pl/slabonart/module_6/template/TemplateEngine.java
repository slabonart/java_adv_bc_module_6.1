package pl.slabonart.module_6.template;

import pl.slabonart.module_6.client.Client;

public interface TemplateEngine {

    String generateMessage(Template template, Client client);
}
