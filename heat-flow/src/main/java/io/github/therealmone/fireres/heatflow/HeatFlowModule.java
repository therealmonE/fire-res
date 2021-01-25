package io.github.therealmone.fireres.heatflow;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.report.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.DefaultSampleEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import io.github.therealmone.fireres.heatflow.pipeline.report.SamplesEnricher;
import io.github.therealmone.fireres.heatflow.pipeline.sample.SampleBoundEnricher;
import io.github.therealmone.fireres.heatflow.pipeline.sample.SampleMeanWithSensorsTemperaturesEnricher;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReportProvider;

import java.util.List;

public class HeatFlowModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HeatFlowReport.class)
                .toProvider(HeatFlowReportProvider.class)
                .in(Singleton.class);
    }

    @Provides
    public ReportEnrichPipeline<HeatFlowReport> enrichPipeline(
            SamplesEnricher samplesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                samplesEnricher
        ));
    }

    @Provides
    public SampleEnrichPipeline<HeatFlowReport, HeatFlowSample> sampleEnrichPipeline(
            SampleBoundEnricher sampleBoundEnricher,
            SampleMeanWithSensorsTemperaturesEnricher sampleMeanWithSensorsTemperaturesEnricher
    ) {
        return new DefaultSampleEnrichPipeline<>(List.of(
                sampleBoundEnricher,
                sampleMeanWithSensorsTemperaturesEnricher
        ));
    }

}
