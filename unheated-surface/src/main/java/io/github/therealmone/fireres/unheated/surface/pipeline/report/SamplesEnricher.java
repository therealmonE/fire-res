package io.github.therealmone.fireres.unheated.surface.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.unheated.surface.pipeline.report.UnheatedSurfaceReportEnrichType.SAMPLES;

public class SamplesEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleEnrichPipeline<UnheatedSurfaceReport, UnheatedSurfaceSample> sampleEnrichPipeline;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        report.setSamples(new ArrayList<>());

        generationProperties.getSamples().forEach(sample -> {
            val unheatedSurfaceSample = new UnheatedSurfaceSample(sample.getId());
            unheatedSurfaceSample.setFirstGroup(new UnheatedSurfaceGroup());
            unheatedSurfaceSample.setSecondGroup(new UnheatedSurfaceGroup());
            unheatedSurfaceSample.setThirdGroup(new UnheatedSurfaceGroup());

            sampleEnrichPipeline.accept(report, unheatedSurfaceSample);

            report.getSamples().add(unheatedSurfaceSample);
        });
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLES.equals(enrichType);
    }
}
