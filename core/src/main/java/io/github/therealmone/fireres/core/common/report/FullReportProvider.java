package io.github.therealmone.fireres.core.common.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.firemode.report.FireModeReportProvider;
import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReportProvider;
import io.github.therealmone.fireres.core.unheated.report.UnheatedSurfaceReportProvider;

public class FullReportProvider implements Provider<FullReport> {

    @Inject
    private GenerationProperties properties;

    @Inject
    private FireModeReportProvider fireMode;

    @Inject
    private ExcessPressureReportProvider excessPressure;

    @Inject
    private UnheatedSurfaceReportProvider unheatedSurface;

    @Override
    public FullReport get() {
        return FullReport.builder()
                .time(properties.getGeneral().getTime())
                .environmentTemperature(properties.getGeneral().getEnvironmentTemperature())
                .fireMode(fireMode.get())
                .excessPressure(excessPressure.get())
                .unheatedSurface(unheatedSurface.get())
                .build();
    }

}
