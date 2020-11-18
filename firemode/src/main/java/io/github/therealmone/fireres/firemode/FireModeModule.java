package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.report.FireModeReportProvider;

public class FireModeModule extends AbstractModule {

    private final GenerationProperties generationProperties;

    public FireModeModule(GenerationProperties generationProperties) {
        this.generationProperties = generationProperties;
    }

    @Override
    protected void configure() {
        install(new CoreModule(generationProperties));
        bind(FireModeReport.class).toProvider(FireModeReportProvider.class);
    }
}
