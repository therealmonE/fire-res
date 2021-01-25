package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.MinAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.MIN_ALLOWED_TEMPERATURE;
import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.SAMPLES;

@Slf4j
public class MinAllowedTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        log.info("Fire mode: enriching report with min allowed temperature");
        val time = generationProperties.getGeneral().getTime();
        val standardTemperature = report.getStandardTemperature();

        val minAllowedTemperature = new MinAllowedTempGenerator(time, standardTemperature)
                .generate();

        report.setMinAllowedTemperature(minAllowedTemperature);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return MIN_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLES);
    }
}
