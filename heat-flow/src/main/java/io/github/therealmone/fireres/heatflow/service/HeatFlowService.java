package io.github.therealmone.fireres.heatflow.service;

import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.service.ReportCreatorService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;

import java.util.List;

public interface HeatFlowService extends ReportCreatorService<HeatFlowReport> {

    void updateSensorsCount(HeatFlowReport report, Integer sensorsCount);

    void updateBound(HeatFlowReport report, Integer bound);

    void updateLinearityCoefficient(HeatFlowReport report, Double linearityCoefficient);

    void updateDispersionCoefficient(HeatFlowReport report, Double dispersionCoefficient);

    void addInterpolationPoints(HeatFlowReport report, List<InterpolationPoint> pointsToAdd);

    void removeInterpolationPoints(HeatFlowReport report, List<InterpolationPoint> pointsToRemove);

}
