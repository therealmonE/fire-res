package io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup;

import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.pipeline.SecondaryGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class SecondGroupMeanWithThermocoupleTemperaturesEnricher extends SecondaryGroupMeanWithThermocoupleTemperaturesEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

    @Override
    protected UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report) {
        return report.getSecondGroup();
    }

    @Override
    protected UnheatedSurfaceGroupProperties getGroupProperties(UnheatedSurfaceReport report) {
        return report.getSample().getSampleProperties().getUnheatedSurface().getSecondGroup();
    }

}