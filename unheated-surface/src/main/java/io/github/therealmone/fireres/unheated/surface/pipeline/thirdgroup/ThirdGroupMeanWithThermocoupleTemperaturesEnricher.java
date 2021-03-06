package io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.model.BoundShift;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.unheated.surface.model.Group;
import io.github.therealmone.fireres.unheated.surface.pipeline.MeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class ThirdGroupMeanWithThermocoupleTemperaturesEnricher extends MeanWithThermocoupleTemperaturesEnricher {

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

    @Override
    protected Group getGroup(UnheatedSurfaceReport report) {
        return report.getThirdGroup();
    }

    @Override
    protected FunctionForm<Integer> getFunctionForm(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getFunctionForm();
    }

    @Override
    protected BoundShift<IntegerPoint> getMeanBoundShift(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift();
    }

    @Override
    protected BoundShift<IntegerPoint> getThermocoupleBoundShift(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift();
    }

    @Override
    protected Integer getThermocoupleCount(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getThermocoupleCount();
    }

}
