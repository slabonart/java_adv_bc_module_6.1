package pl.slabonart.module_6.template;

import pl.slabonart.module_6.loader.FileValuesLoader;
import pl.slabonart.module_6.loader.ValuesLoader;


public class FileModeTemplateEngine extends BaseTemplateEngine {

    public FileModeTemplateEngine(String fileName) {
        this.valuesLoader = new FileValuesLoader(fileName);
    }

    public FileModeTemplateEngine(ValuesLoader valuesLoader) {
        this.valuesLoader = valuesLoader;
    }

    @Override
    protected void loadValues() {
        values = valuesLoader.loadValues();
    }
}
