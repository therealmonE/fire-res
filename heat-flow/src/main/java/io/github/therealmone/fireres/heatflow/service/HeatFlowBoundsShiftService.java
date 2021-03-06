package io.github.therealmone.fireres.heatflow.service;

import io.github.therealmone.fireres.core.service.BoundsShiftService;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;

public interface HeatFlowBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedFlowShift(HeatFlowReport report, HeatFlowPoint shift);

    void removeMaxAllowedFlowShift(HeatFlowReport report, HeatFlowPoint shift);

}
