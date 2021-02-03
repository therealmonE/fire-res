package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.pipeline.MeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.firemode.pipeline.FurnaceTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.MaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.MinAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.StandardTemperatureEnricher;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.firemode.service.impl.FireModeServiceImpl;

import java.util.List;

public class FireModeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FurnaceTemperatureEnricher.class).in(Singleton.class);
        bind(StandardTemperatureEnricher.class).in(Singleton.class);
        bind(MinAllowedTemperatureEnricher.class).in(Singleton.class);
        bind(MaxAllowedTemperatureEnricher.class).in(Singleton.class);
        bind(MeanWithThermocoupleTemperaturesEnricher.class).in(Singleton.class);

        bind(FireModeService.class).to(FireModeServiceImpl.class);
    }

    @Provides
    @Singleton
    public ReportEnrichPipeline<FireModeReport> enrichPipeline(
            StandardTemperatureEnricher standardTemperatureEnricher,
            MinAllowedTemperatureEnricher minAllowedTemperatureEnricher,
            MaxAllowedTemperatureEnricher maxAllowedTemperatureEnricher,
            FurnaceTemperatureEnricher furnaceTemperatureEnricher,
            MeanWithThermocoupleTemperaturesEnricher meanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                standardTemperatureEnricher,
                minAllowedTemperatureEnricher,
                maxAllowedTemperatureEnricher,
                furnaceTemperatureEnricher,
                meanWithThermocoupleTemperaturesEnricher
        ));
    }

}
