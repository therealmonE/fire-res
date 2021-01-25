package io.github.therealmone.fireres.excess.pressure.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.excess.pressure.generator.SamplePressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_PRESSURE;

@Slf4j
public class SamplePressureEnricher implements SampleEnricher<ExcessPressureReport, ExcessPressureSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report, ExcessPressureSample sample) {
        log.info("Excess pressure: enriching sample {} with pressure", sample.getId());
        val time = generationProperties.getGeneral().getTime();
        val minAllowedPressure = report.getMinAllowedPressure();
        val maxAllowedPressure = report.getMaxAllowedPressure();
        val dispersion = generationProperties.getGeneral().getExcessPressure().getDispersionCoefficient();

        val samplePressure = new SamplePressureGenerator(
                time, minAllowedPressure, maxAllowedPressure, dispersion)
                .generate();

        sample.setPressure(samplePressure);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_PRESSURE.equals(enrichType);
    }

}
