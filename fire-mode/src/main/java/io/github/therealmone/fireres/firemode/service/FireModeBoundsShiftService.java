package io.github.therealmone.fireres.firemode.service;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.service.BoundsShiftService;
import io.github.therealmone.fireres.firemode.report.FireModeReport;

public interface FireModeBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedTemperatureShift(FireModeReport report, IntegerPoint shift);

    void removeMaxAllowedTemperatureShift(FireModeReport report, IntegerPoint shift);

    void addMinAllowedTemperatureShift(FireModeReport report, IntegerPoint shift);

    void removeMinAllowedTemperatureShift(FireModeReport report, IntegerPoint shift);

}
