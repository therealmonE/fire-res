package io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.pipeline.PrimaryGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class FirstGroupMeanWithThermocoupleTemperaturesEnricher extends PrimaryGroupMeanWithThermocoupleTemperaturesEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

    @Override
    protected UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report) {
        return report.getFirstGroup();
    }

}
