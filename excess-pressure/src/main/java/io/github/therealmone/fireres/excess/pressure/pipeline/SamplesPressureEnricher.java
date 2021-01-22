package io.github.therealmone.fireres.excess.pressure.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.excess.pressure.generator.SamplePressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureEnrichType.SAMPLES_PRESSURE;

public class SamplesPressureEnricher implements ReportEnricher<ExcessPressureReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val time = generationProperties.getGeneral().getTime();
        val minAllowedPressure = report.getMinAllowedPressure();
        val maxAllowedPressure = report.getMaxAllowedPressure();
        val dispersion = generationProperties.getGeneral().getExcessPressure().getDispersionCoefficient();

        report.setSamples(new ArrayList<>());

        generationProperties.getSamples().forEach(sample -> {
            val samplePressure = new SamplePressureGenerator(
                    time, minAllowedPressure, maxAllowedPressure, dispersion)
                    .generate();

            report.getSamples().add(new ExcessPressureSample(samplePressure));
        });

    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLES_PRESSURE.equals(enrichType);
    }
}
