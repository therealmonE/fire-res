package io.github.therealmone.fireres.unheated.surface.pipeline.sample.thirdgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleBound;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_THIRD_GROUP_THERMOCOUPLE_BOUND;

@Slf4j
public class SampleThirdGroupThermocoupleBoundEnricher implements SampleEnricher<UnheatedSurfaceReport, UnheatedSurfaceSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report, UnheatedSurfaceSample sample) {
        log.info("Unheated surface: enriching sample {} third group with thermocouple bound", sample.getId());
        val sampleProperties = generationProperties.getSampleById(sample.getId());
        val groupProperties = sampleProperties.getUnheatedSurface().getThirdGroup();
        val time = generationProperties.getGeneral().getTime();

        val thermocoupleBound = new UnheatedSurfaceThermocoupleBound(
                constantFunction(time, groupProperties.getBound()).getValue());

        sample.getThirdGroup().setThermocoupleBound(thermocoupleBound);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_THIRD_GROUP_THERMOCOUPLE_BOUND.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLE_THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

}
