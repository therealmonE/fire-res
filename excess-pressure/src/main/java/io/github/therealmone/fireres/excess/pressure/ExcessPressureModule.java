package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.report.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.DefaultSampleEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.pipeline.sample.BasePressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.sample.MaxAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.sample.MinAllowedPressureEnricher;
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
            SamplesEnricher samplesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                samplesEnricher
        ));
    }

    @Provides
    public SampleEnrichPipeline<ExcessPressureReport, ExcessPressureSample> sampleEnrichPipeline(
            BasePressureEnricher basePressureEnricher,
            MinAllowedPressureEnricher minAllowedPressureEnricher,
            MaxAllowedPressureEnricher maxAllowedPressureEnricher,
            SamplePressureEnricher samplePressureEnricher
    ) {
        return new DefaultSampleEnrichPipeline<>(List.of(
                basePressureEnricher,
                minAllowedPressureEnricher,
                maxAllowedPressureEnricher,
                samplePressureEnricher
        ));
    }

}
