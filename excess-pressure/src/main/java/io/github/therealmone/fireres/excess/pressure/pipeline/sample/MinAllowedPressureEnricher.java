package io.github.therealmone.fireres.excess.pressure.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.excess.pressure.generator.MinAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_MIN_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_PRESSURE;

@Slf4j
public class MinAllowedPressureEnricher implements SampleEnricher<ExcessPressureReport, ExcessPressureSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report, ExcessPressureSample sample) {
        log.info("Excess pressure: enriching sample {} with min allowed pressure", sample.getId());
        val time = generationProperties.getGeneral().getTime();
        val sampleProperties = generationProperties.getSampleById(sample.getId());
        val delta = sampleProperties.getExcessPressure().getDelta();

        val minAllowedPressure = new MinAllowedPressureGenerator(time, delta)
                .generate();

        sample.setMinAllowedPressure(minAllowedPressure);
    }

    @Override
    public boolean supports(SampleEnrichType enrichType) {
        return SAMPLE_MIN_ALLOWED_PRESSURE.equals(enrichType);
    }

    @Override
    public List<SampleEnrichType> getAffectedTypes() {
        return List.of(SAMPLE_PRESSURE);
    }
}
