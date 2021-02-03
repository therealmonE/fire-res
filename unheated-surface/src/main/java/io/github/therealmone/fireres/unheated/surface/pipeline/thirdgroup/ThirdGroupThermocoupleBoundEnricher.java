package io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.pipeline.SecondaryThermocoupleBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_THERMOCOUPLE_BOUND;

@Slf4j
public class ThirdGroupThermocoupleBoundEnricher extends SecondaryThermocoupleBoundEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return THIRD_GROUP_THERMOCOUPLE_BOUND.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report) {
        return report.getThirdGroup();
    }

    @Override
    protected UnheatedSurfaceSecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report) {
        return report.getSample().getSampleProperties().getUnheatedSurface().getThirdGroup();
    }
}
