package io.github.therealmone.fireres.excess.pressure.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_BASE_PRESSURE;

@Slf4j
public class BasePressureEnricher implements SampleEnricher<ExcessPressureReport, ExcessPressureSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report, ExcessPressureSample sample) {
        log.info("Excess pressure: enriching sample {} with base pressure", sample.getId());
        val sampleProperties = generationProperties.getSampleById(sample.getId());

        sample.setBasePressure(sampleProperties.getExcessPressure().getBasePressure());
    }

    @Override
    public boolean supports(SampleEnrichType enrichType) {
        return SAMPLE_BASE_PRESSURE.equals(enrichType);
    }
}
