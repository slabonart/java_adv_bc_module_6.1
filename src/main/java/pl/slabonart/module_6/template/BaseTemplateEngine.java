package pl.slabonart.module_6.template;

import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.loader.ValuesLoader;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class BaseTemplateEngine implements TemplateEngine {

    private static final String OPENING_TAG = "#{";
    private static final String CLOSING_TAG = "}";

    protected Map<String, String> values;

    protected ValuesLoader valuesLoader;

    protected abstract void loadValues();

    @Override
    public String generateMessage(Template template, Client client) {

        loadValues();

        String message = template.getMessage();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            message = message.replaceAll(prepareRegEx(entry.getKey()), entry.getValue());
        }
        return message;
    }

    private String prepareRegEx(String value) {
        return Pattern.quote(OPENING_TAG + value + CLOSING_TAG);
    }
}
