package io.github.therealmone.fireres.excess.pressure.service;

import io.github.therealmone.fireres.core.service.ReportCreatorService;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;

public interface ExcessPressureService extends ReportCreatorService<ExcessPressureReport> {

    void updateBasePressure(ExcessPressureReport report, Double basePressure);

    void updateDelta(ExcessPressureReport report, Double delta);

    void updateDispersionCoefficient(ExcessPressureReport report, Double dispersionCoefficient);

}
