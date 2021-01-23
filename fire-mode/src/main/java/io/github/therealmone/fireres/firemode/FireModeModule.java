package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.report.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.DefaultSampleEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.pipeline.report.SampleMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.firemode.pipeline.sample.FurnaceTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.sample.MaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.sample.MinAllowedTemperatureEnricher;
import io.github.therealmone.fireres.firemode.pipeline.sample.SamplesTemperatureSEnricher;
import io.github.therealmone.fireres.firemode.pipeline.sample.StandardTemperatureEnricher;
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

    @Provides
    public SampleEnrichPipeline<FireModeReport, FireModeSample> sampleEnrichPipeline(
            SampleMeanWithThermocoupleTemperaturesEnricher sampleMeanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultSampleEnrichPipeline<>(List.of(
                sampleMeanWithThermocoupleTemperaturesEnricher
        ));
    }

}
