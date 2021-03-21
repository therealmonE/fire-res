package io.github.therealmone.fireres.firemode.service;

import io.github.therealmone.fireres.core.service.InterpolationService;
import io.github.therealmone.fireres.core.service.ReportCreatorService;
import io.github.therealmone.fireres.firemode.model.FireModeType;
import io.github.therealmone.fireres.firemode.report.FireModeReport;

public interface FireModeService extends
        ReportCreatorService<FireModeReport>,
        InterpolationService<FireModeReport, Integer>,
        FireModeBoundsShiftService {

    void updateThermocoupleCount(FireModeReport report, Integer thermocoupleCount);

    void updateFireModeType(FireModeReport report, FireModeType fireModeType);

}
