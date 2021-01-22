package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.pipeline.FurnaceTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.MaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.MinAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.SamplesTemperatureSEnricher;
import io.github.therealmone.fireres.firemode.pipeline.StandardTemperatureEnricher;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.report.FireModeReportProvider;

import java.util.List;

public class FireModeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FireModeReport.class)
                .toProvider(FireModeReportProvider.class)
                .in(Singleton.class);

    }

    @Provides
    public ReportEnrichPipeline<FireModeReport> enrichPipeline(
            StandardTemperatureEnricher standardTemperatureEnricher,
            MinAllowedTemperatureEnricher minAllowedTemperatureEnricher,
            MaxAllowedTemperatureEnricher maxAllowedTemperatureEnricher,
            FurnaceTemperatureEnricher furnaceTemperatureEnricher,
            SamplesTemperatureSEnricher samplesTemperatureSEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                standardTemperatureEnricher,
                minAllowedTemperatureEnricher,
                maxAllowedTemperatureEnricher,
                furnaceTemperatureEnricher,
                samplesTemperatureSEnricher
        ));
    }

}
