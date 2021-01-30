package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.MaxAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.MAX_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.SAMPLES;

@Slf4j
public class MaxAllowedTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        log.info("Fire mode: enriching report with max allowed temperature");
        val time = generationProperties.getGeneral().getTime();
        val standardTemperature = report.getStandardTemperature();

        val maxAllowedTemperature = new MaxAllowedTempGenerator(time, standardTemperature)
                .generate();

        report.setMaxAllowedTemperature(maxAllowedTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MAX_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(SAMPLES);
    }
}
