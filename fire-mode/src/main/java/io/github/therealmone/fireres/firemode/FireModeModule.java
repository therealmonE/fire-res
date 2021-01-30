package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.report.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.DefaultSampleEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.pipeline.sample.SampleMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.firemode.pipeline.report.FurnaceTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.report.MaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.report.MinAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.report.SamplesEnricher;
import io.github.therealmone.fireres.firemode.pipeline.report.StandardTemperatureEnricher;
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
    @Singleton
    public ReportEnrichPipeline<FireModeReport> enrichPipeline(
            StandardTemperatureEnricher standardTemperatureEnricher,
            MinAllowedTemperatureEnricher minAllowedTemperatureEnricher,
            MaxAllowedTemperatureEnricher maxAllowedTemperatureEnricher,
            FurnaceTemperatureEnricher furnaceTemperatureEnricher,
            SamplesEnricher samplesTemperatureSEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                standardTemperatureEnricher,
                minAllowedTemperatureEnricher,
                maxAllowedTemperatureEnricher,
                furnaceTemperatureEnricher,
                samplesTemperatureSEnricher
        ));
    }

    @Provides
    @Singleton
    public SampleEnrichPipeline<FireModeReport, FireModeSample> sampleEnrichPipeline(
            SampleMeanWithThermocoupleTemperaturesEnricher sampleMeanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultSampleEnrichPipeline<>(List.of(
                sampleMeanWithThermocoupleTemperaturesEnricher
        ));
    }

}
