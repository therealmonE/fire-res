package io.github.therealmone.fireres.heatflow.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.heatflow.pipeline.report.HeatFlowReportEnrichType.SAMPLES;

public class SamplesEnricher implements ReportEnricher<HeatFlowReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleEnrichPipeline<HeatFlowReport, HeatFlowSample> sampleEnrichPipeline;

    @Override
    public void enrich(HeatFlowReport report) {
        report.setSamples(new ArrayList<>());

        generationProperties.getSamples().forEach(sample -> {
            val heatFlowSample = new HeatFlowSample(sample.getId());

            sampleEnrichPipeline.accept(report, heatFlowSample);

            report.getSamples().add(heatFlowSample);
        });
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLES.equals(enrichType);
    }

}
