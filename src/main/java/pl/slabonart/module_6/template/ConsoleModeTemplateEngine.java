package pl.slabonart.module_6.template;

import pl.slabonart.module_6.loader.ValuesLoader;

public class ConsoleModeTemplateEngine extends BaseTemplateEngine {

    public ConsoleModeTemplateEngine(ValuesLoader valuesLoader) {
        this.valuesLoader = valuesLoader;
    }

    @Override
    protected void loadValues() {
        values = valuesLoader.loadValues();
    }
}
