package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.StandardTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.FURNACE_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.MAX_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.MIN_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.SAMPLES;
import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.STANDARD_TEMPERATURE;

public class StandardTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();

        val standardTemperature = new StandardTempGenerator(t0, time)
                .generate();

        report.setStandardTemperature(standardTemperature);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return STANDARD_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(
                MIN_ALLOWED_TEMPERATURE,
                MAX_ALLOWED_TEMPERATURE,
                FURNACE_TEMPERATURE,
                SAMPLES);
    }
}
