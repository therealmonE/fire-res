package io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.pipeline.SecondaryGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class ThirdGroupMeanWithThermocoupleTemperaturesEnricher extends SecondaryGroupMeanWithThermocoupleTemperaturesEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

    @Override
    protected UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report) {
        return report.getThirdGroup();
    }

    @Override
    protected UnheatedSurfaceGroupProperties getGroupProperties(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup();
    }

}
