package io.github.therealmone.fireres.excess.pressure.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.excess.pressure.pipeline.report.ExcessPressureReportEnrichType.SAMPLES;

public class SamplesEnricher implements ReportEnricher<ExcessPressureReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleEnrichPipeline<ExcessPressureReport, ExcessPressureSample> sampleEnrichPipeline;

    @Override
    public void enrich(ExcessPressureReport report) {
        report.setSamples(new ArrayList<>());

        generationProperties.getSamples().forEach(sample -> {
            val excessPressureSample = new ExcessPressureSample(sample.getId());
            sampleEnrichPipeline.accept(report, excessPressureSample);

            report.getSamples().add(excessPressureSample);
        });
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLES.equals(enrichType);
    }
}
