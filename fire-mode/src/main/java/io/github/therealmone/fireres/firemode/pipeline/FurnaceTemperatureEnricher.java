package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.FurnaceTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.FURNACE_TEMPERATURE;

@Slf4j
public class FurnaceTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();
        val standardTemperature = report.getStandardTemperature();

        val furnaceTemperature = new FurnaceTempGenerator(t0, standardTemperature)
                .generate();

        report.setFurnaceTemperature(furnaceTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FURNACE_TEMPERATURE.equals(enrichType);
    }

}
