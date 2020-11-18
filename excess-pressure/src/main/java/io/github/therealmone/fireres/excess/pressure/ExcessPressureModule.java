package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReportProvider;

public class ExcessPressureModule extends AbstractModule {

    private final GenerationProperties generationProperties;

    public ExcessPressureModule(GenerationProperties generationProperties) {
        this.generationProperties = generationProperties;
    }

    @Override
    protected void configure() {
        install(new CoreModule(generationProperties));
        bind(ExcessPressureReport.class).toProvider(ExcessPressureReportProvider.class);
    }

}
