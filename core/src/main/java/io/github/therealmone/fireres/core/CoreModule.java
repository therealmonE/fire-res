package io.github.therealmone.fireres.core;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.report.FullReport;
import io.github.therealmone.fireres.core.common.report.FullReportProvider;
import io.github.therealmone.fireres.core.firemode.report.FireModeReport;
import io.github.therealmone.fireres.core.firemode.report.FireModeReportProvider;
import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReportProvider;
import io.github.therealmone.fireres.core.unheated.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.core.unheated.report.UnheatedSurfaceReportProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreModule extends AbstractModule {

    private final GenerationProperties properties;

    @Override
    protected void configure() {
        bind(FireModeReport.class).toProvider(FireModeReportProvider.class);
        bind(ExcessPressureReport.class).toProvider(ExcessPressureReportProvider.class);
        bind(UnheatedSurfaceReport.class).toProvider(UnheatedSurfaceReportProvider.class);
        bind(FullReport.class).toProvider(FullReportProvider.class);

        bind(GenerationProperties.class).toInstance(properties);
    }

}
