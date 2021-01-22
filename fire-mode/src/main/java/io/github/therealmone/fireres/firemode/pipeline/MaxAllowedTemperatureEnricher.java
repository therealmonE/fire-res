package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.MaxAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeEnrichType.MAX_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeEnrichType.SAMPLES_TEMPERATURE;

public class MaxAllowedTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        val time = generationProperties.getGeneral().getTime();
        val standardTemperature = report.getStandardTemperature();

        val maxAllowedTemperature = new MaxAllowedTempGenerator(time, standardTemperature)
                .generate();

        report.setMaxAllowedTemperature(maxAllowedTemperature);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return MAX_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLES_TEMPERATURE);
    }
}
