package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.StandardTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.FURNACE_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MAX_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MIN_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.STANDARD_TEMPERATURE;

@Slf4j
public class StandardTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();
        val properties = report.getProperties();

        val standardTemperature = new StandardTempGenerator(t0, time, properties.getFireModeType())
                .generate();

        report.setStandardTemperature(standardTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return STANDARD_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(
                MIN_ALLOWED_TEMPERATURE,
                MAX_ALLOWED_TEMPERATURE,
                FURNACE_TEMPERATURE,
                MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }
}
