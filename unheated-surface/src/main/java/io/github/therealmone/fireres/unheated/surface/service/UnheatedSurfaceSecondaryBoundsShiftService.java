package io.github.therealmone.fireres.unheated.surface.service;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.service.BoundsShiftService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceSecondaryBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void removeMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

}
