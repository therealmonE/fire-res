package io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.unheated.surface.generator.MaxAllowedThermocoupleTemperatureGenerator;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE;

@Slf4j
public class FirstGroupMaxAllowedThermocoupleTemperatureEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();

        val thermocoupleBound = new MaxAllowedThermocoupleTemperatureGenerator(time, t0)
                .generate();

        report.getFirstGroup().setMaxAllowedThermocoupleTemperature(thermocoupleBound);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FIRST_GROUP_MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }
}
