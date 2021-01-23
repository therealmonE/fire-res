package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.report.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.DefaultSampleEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.pipeline.report.MaxAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.report.MinAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.report.SamplesEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.sample.SamplePressureEnricher;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReportProvider;

import java.util.List;

public class ExcessPressureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExcessPressureReport.class)
                .toProvider(ExcessPressureReportProvider.class)
                .in(Singleton.class);
    }

    @Provides
    public ReportEnrichPipeline<ExcessPressureReport> enrichPipeline(
            MinAllowedPressureEnricher minAllowedPressureEnricher,
            MaxAllowedPressureEnricher maxAllowedPressureEnricher,
            SamplesEnricher samplesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                minAllowedPressureEnricher,
                maxAllowedPressureEnricher,
                samplesEnricher
        ));
    }

    @Provides
    public SampleEnrichPipeline<ExcessPressureReport, ExcessPressureSample> sampleEnrichPipeline(
            SamplePressureEnricher samplePressureEnricher
    ) {
        return new DefaultSampleEnrichPipeline<>(List.of(
                samplePressureEnricher
        ));
    }

}
