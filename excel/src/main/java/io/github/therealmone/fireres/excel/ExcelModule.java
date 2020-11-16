package io.github.therealmone.fireres.excel;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;

public class ExcelModule extends AbstractModule {

    private final GenerationProperties properties;

    public ExcelModule(GenerationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        install(new CoreModule(properties));
        bind(ReportConstructor.class).to(ExcelReportConstructor.class);
    }



}
