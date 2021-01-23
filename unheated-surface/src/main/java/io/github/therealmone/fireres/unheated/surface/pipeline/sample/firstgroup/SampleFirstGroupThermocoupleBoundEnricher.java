package io.github.therealmone.fireres.unheated.surface.pipeline.sample.firstgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceThermocoupleBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_FIRST_GROUP_THERMOCOUPLE_BOUND;

public class SampleFirstGroupThermocoupleBoundEnricher implements SampleEnricher<UnheatedSurfaceReport, UnheatedSurfaceSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report, UnheatedSurfaceSample sample) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();

        val thermocoupleBound = new UnheatedSurfaceThermocoupleBoundGenerator(time, t0)
                .generate();

        sample.getFirstGroup().setThermocoupleBound(thermocoupleBound);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_FIRST_GROUP_THERMOCOUPLE_BOUND.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

}
