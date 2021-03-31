package io.github.therealmone.fireres.firemode.pipeline;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.MaxAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MAINTAINED_TEMPERATURES;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MAX_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class MaxAllowedTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Override
    public void enrich(FireModeReport report) {
        val standardTemperature = report.getStandardTemperature();

        val maxAllowedTemperature = new MaxAllowedTempGenerator(standardTemperature)
                .generate();

        report.setMaxAllowedTemperature(maxAllowedTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MAX_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(MEAN_WITH_THERMOCOUPLE_TEMPERATURES, MAINTAINED_TEMPERATURES);
    }
}
