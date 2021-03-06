package io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.therealmone.fireres.unheated.surface.config.SecondaryGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.Group;
import io.github.therealmone.fireres.unheated.surface.pipeline.SecondaryGroupMaxAllowedTemperatureEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MAX_ALLOWED_TEMPERATURE;

@Slf4j
public class ThirdGroupMaxAllowedTemperatureEnricher extends SecondaryGroupMaxAllowedTemperatureEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return THIRD_GROUP_MAX_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected Group getGroup(UnheatedSurfaceReport report) {
        return report.getThirdGroup();
    }

    @Override
    protected SecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup();
    }
}
