package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.FurnaceTempGenerator;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeEnrichType.FURNACE_TEMPERATURE;


public class FurnaceTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();
        val standardTemperature = report.getStandardTemperature();

        val furnaceTemperature = new FurnaceTempGenerator(time, t0, standardTemperature)
                .generate();

        report.setFurnaceTemperature(furnaceTemperature);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return FURNACE_TEMPERATURE.equals(enrichType);
    }

}
