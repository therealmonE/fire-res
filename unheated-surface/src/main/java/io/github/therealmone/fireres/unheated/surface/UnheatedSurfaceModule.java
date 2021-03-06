package io.github.therealmone.fireres.unheated.surface;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup.FirstGroupMaxAllowedMeanTemperatureEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup.FirstGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup.FirstGroupMaxAllowedThermocoupleTemperatureEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup.SecondGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup.SecondGroupMaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup.ThirdGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup.ThirdGroupMaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import io.github.therealmone.fireres.unheated.surface.service.impl.UnheatedSurfaceFirstGroupServiceImpl;
import io.github.therealmone.fireres.unheated.surface.service.impl.UnheatedSurfaceSecondGroupServiceImpl;
import io.github.therealmone.fireres.unheated.surface.service.impl.UnheatedSurfaceServiceImpl;
import io.github.therealmone.fireres.unheated.surface.service.impl.UnheatedSurfaceThirdGroupServiceImpl;

import java.util.List;

public class UnheatedSurfaceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FirstGroupMaxAllowedMeanTemperatureEnricher.class).in(Singleton.class);
        bind(FirstGroupMaxAllowedThermocoupleTemperatureEnricher.class).in(Singleton.class);
        bind(FirstGroupMeanWithThermocoupleTemperaturesEnricher.class).in(Singleton.class);
        bind(SecondGroupMaxAllowedTemperatureEnricher.class).in(Singleton.class);
        bind(SecondGroupMeanWithThermocoupleTemperaturesEnricher.class).in(Singleton.class);
        bind(ThirdGroupMaxAllowedTemperatureEnricher.class).in(Singleton.class);
        bind(ThirdGroupMeanWithThermocoupleTemperaturesEnricher.class).in(Singleton.class);

        bind(UnheatedSurfaceService.class).to(UnheatedSurfaceServiceImpl.class).in(Singleton.class);
        bind(UnheatedSurfaceFirstGroupService.class).to(UnheatedSurfaceFirstGroupServiceImpl.class).in(Singleton.class);
        bind(UnheatedSurfaceSecondGroupService.class).to(UnheatedSurfaceSecondGroupServiceImpl.class).in(Singleton.class);
        bind(UnheatedSurfaceThirdGroupService.class).to(UnheatedSurfaceThirdGroupServiceImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public ReportEnrichPipeline<UnheatedSurfaceReport> enrichPipeline(
            FirstGroupMaxAllowedMeanTemperatureEnricher firstGroupMaxAllowedMeanTemperatureEnricher,
            FirstGroupMaxAllowedThermocoupleTemperatureEnricher firstGroupMaxAllowedThermocoupleTemperatureEnricher,
            FirstGroupMeanWithThermocoupleTemperaturesEnricher firstGroupMeanWithThermocoupleTemperaturesEnricher,

            SecondGroupMaxAllowedTemperatureEnricher secondGroupMaxAllowedTemperatureEnricher,
            SecondGroupMeanWithThermocoupleTemperaturesEnricher secondGroupMeanWithThermocoupleTemperaturesEnricher,

            ThirdGroupMaxAllowedTemperatureEnricher thirdGroupMaxAllowedTemperatureEnricher,
            ThirdGroupMeanWithThermocoupleTemperaturesEnricher thirdGroupMeanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                firstGroupMaxAllowedMeanTemperatureEnricher,
                firstGroupMaxAllowedThermocoupleTemperatureEnricher,
                firstGroupMeanWithThermocoupleTemperaturesEnricher,
                secondGroupMaxAllowedTemperatureEnricher,
                secondGroupMeanWithThermocoupleTemperaturesEnricher,
                thirdGroupMaxAllowedTemperatureEnricher,
                thirdGroupMeanWithThermocoupleTemperaturesEnricher
        ));
    }

}
