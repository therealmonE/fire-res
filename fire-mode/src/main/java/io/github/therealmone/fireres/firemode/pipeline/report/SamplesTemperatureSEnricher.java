package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.SAMPLES;

@Slf4j
public class SamplesTemperatureSEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleEnrichPipeline<FireModeReport, FireModeSample> sampleEnrichPipeline;

    @Override
    public void enrich(FireModeReport report) {
        log.info("Fire mode: enriching report with samples");
        report.setSamples(new ArrayList<>());

        generationProperties.getSamples().forEach(sample -> {
            val fireModeSample = new FireModeSample(sample.getId());

            sampleEnrichPipeline.accept(report, fireModeSample);

            report.getSamples().add(fireModeSample);
        });
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLES.equals(enrichType);
    }
}
