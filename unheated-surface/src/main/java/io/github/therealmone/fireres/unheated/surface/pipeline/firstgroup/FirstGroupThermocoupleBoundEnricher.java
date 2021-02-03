package io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.pipeline.PrimaryThermocoupleBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_THERMOCOUPLE_BOUND;

@Slf4j
public class FirstGroupThermocoupleBoundEnricher extends PrimaryThermocoupleBoundEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FIRST_GROUP_THERMOCOUPLE_BOUND.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report) {
        return report.getFirstGroup();
    }
}
