package io.github.therealmone.fireres.heatflow.service;

import io.github.therealmone.fireres.core.service.InterpolationService;
import io.github.therealmone.fireres.core.service.ReportCreatorService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;

public interface HeatFlowService extends ReportCreatorService<HeatFlowReport>, InterpolationService<HeatFlowReport, Double> {

    void updateSensorsCount(HeatFlowReport report, Integer sensorsCount);

    void updateBound(HeatFlowReport report, Double bound);

}
