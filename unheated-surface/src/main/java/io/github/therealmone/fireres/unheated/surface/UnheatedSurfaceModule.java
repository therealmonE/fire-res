package io.github.therealmone.fireres.unheated.surface;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.unheated.surface.factory.UnheatedSurfaceFactory;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReportProvider;

public class UnheatedSurfaceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UnheatedSurfaceReport.class).toProvider(UnheatedSurfaceReportProvider.class);
        bind(UnheatedSurfaceFactory.class);
    }

}
