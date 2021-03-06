package io.github.therealmone.fireres.excess.pressure.service;

import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;

public interface ExcessPressureBoundsShiftService {

    void addMaxAllowedPressureShift(ExcessPressureReport report, DoublePoint shift);

    void removeMaxAllowedPressureShift(ExcessPressureReport report, DoublePoint shift);

    void addMinAllowedPressureShift(ExcessPressureReport report, DoublePoint shift);

    void removeMinAllowedPressureShift(ExcessPressureReport report, DoublePoint shift);

}
