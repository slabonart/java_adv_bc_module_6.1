package pl.slabonart.module_6.exception;

public class ValueForPlaceholderNotProvidedException extends Exception {

    private static final String MESSAGE = "Value for placeholder '%s' was not provided";

    public ValueForPlaceholderNotProvidedException(String placeholderName) {
        super(String.format(MESSAGE, placeholderName));
    }
}
