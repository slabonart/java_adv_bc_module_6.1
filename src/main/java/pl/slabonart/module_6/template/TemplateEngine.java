package pl.slabonart.module_6.template;

import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.exception.ValueForPlaceholderNotProvidedException;

public interface TemplateEngine {

    String generateMessage(Template template, Client client) throws ValueForPlaceholderNotProvidedException;
}
