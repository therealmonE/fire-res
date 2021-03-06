package io.github.therealmone.fireres.unheated.surface.service;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.service.BoundsShiftService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfacePrimaryBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void removeMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void addMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void removeMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

}
