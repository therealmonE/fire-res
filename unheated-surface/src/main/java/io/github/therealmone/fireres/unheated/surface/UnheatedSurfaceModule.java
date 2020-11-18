package io.github.therealmone.fireres.unheated.surface;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReportProvider;

public class UnheatedSurfaceModule extends AbstractModule {

    private final GenerationProperties generationProperties;

    public UnheatedSurfaceModule(GenerationProperties generationProperties) {
        this.generationProperties = generationProperties;
    }

    @Override
    protected void configure() {
        install(new CoreModule(generationProperties));
        bind(UnheatedSurfaceReport.class).toProvider(UnheatedSurfaceReportProvider.class);
    }
}
