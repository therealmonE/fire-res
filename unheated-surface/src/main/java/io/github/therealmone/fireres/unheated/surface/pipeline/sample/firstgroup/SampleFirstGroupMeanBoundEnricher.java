package io.github.therealmone.fireres.unheated.surface.pipeline.sample.firstgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceMeanBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_FIRST_GROUP_MEAN_BOUND;
import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

public class SampleFirstGroupMeanBoundEnricher implements SampleEnricher<UnheatedSurfaceReport, UnheatedSurfaceSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report, UnheatedSurfaceSample sample) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();

        val meanBound = new UnheatedSurfaceMeanBoundGenerator(time, t0)
                .generate();

        sample.getFirstGroup().setMeanBound(meanBound);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_FIRST_GROUP_MEAN_BOUND.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

}
