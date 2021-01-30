package io.github.therealmone.fireres.excess.pressure.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.excess.pressure.generator.MaxAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_MAX_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_PRESSURE;

@Slf4j
public class MaxAllowedPressureEnricher implements SampleEnricher<ExcessPressureReport, ExcessPressureSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report, ExcessPressureSample sample) {
        log.info("Excess pressure: enriching sample {} with max allowed pressure", sample.getId());
        val time = generationProperties.getGeneral().getTime();
        val sampleProperties = generationProperties.getSampleById(sample.getId());
        val delta = sampleProperties.getExcessPressure().getDelta();

        val maxAllowedPressure = new MaxAllowedPressureGenerator(time, delta)
                .generate();

        sample.setMaxAllowedPressure(maxAllowedPressure);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_MAX_ALLOWED_PRESSURE.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLE_PRESSURE);
    }
}
