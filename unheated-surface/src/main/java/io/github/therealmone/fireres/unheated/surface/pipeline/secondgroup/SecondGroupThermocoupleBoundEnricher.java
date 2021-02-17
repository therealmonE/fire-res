package io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup;

import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.pipeline.SecondaryThermocoupleBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_THERMOCOUPLE_BOUND;

@Slf4j
public class SecondGroupThermocoupleBoundEnricher extends SecondaryThermocoupleBoundEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return SECOND_GROUP_THERMOCOUPLE_BOUND.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report) {
        return report.getSecondGroup();
    }

    @Override
    protected UnheatedSurfaceSecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report) {
        return report.getProperties().getSecondGroup();
    }
}
