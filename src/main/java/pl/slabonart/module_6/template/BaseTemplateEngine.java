package pl.slabonart.module_6.template;

import pl.slabonart.module_6.client.Client;
import pl.slabonart.module_6.exception.ValueForPlaceholderNotProvidedException;
import pl.slabonart.module_6.loader.ValuesLoader;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseTemplateEngine implements TemplateEngine {

    private static final String OPENING_TAG = "#{";
    private static final String CLOSING_TAG = "}";
    private static final String PLACEHOLDER_REGEX = "#\\{(\\w+)}";

    protected Map<String, String> values;

    protected ValuesLoader valuesLoader;

    protected abstract void loadValues();

    @Override
    public String generateMessage(Template template, Client client) throws ValueForPlaceholderNotProvidedException {

        loadValues();

        String message = template.getMessage();

        validatePlaceholdersValues(message);

        for (Map.Entry<String, String> entry : values.entrySet()) {
            message = message.replaceAll(prepareRegEx(entry.getKey()), entry.getValue());
        }
        return message;
    }

    private void validatePlaceholdersValues(String message) throws ValueForPlaceholderNotProvidedException {
        Set<String> placeholders = findPlaceholders(message);
                for (String placeholder : placeholders) {
            if (!values.containsKey(placeholder)) {
                throw new ValueForPlaceholderNotProvidedException(placeholder);
            }
        }
    }

    private String prepareRegEx(String value) {
        return Pattern.quote(OPENING_TAG + value + CLOSING_TAG);
    }

    private Set<String> findPlaceholders(String messageTemplate) {
        Set<String> placeholders = new HashSet<>();
        Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(messageTemplate);
        while (matcher.find()) {
            placeholders.add(matcher.group(1));
        }
        return placeholders;
    }
}
