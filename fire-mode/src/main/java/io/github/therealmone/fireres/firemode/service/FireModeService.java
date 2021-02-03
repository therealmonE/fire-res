package io.github.therealmone.fireres.firemode.service;

import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.service.ReportCreatorService;
import io.github.therealmone.fireres.firemode.report.FireModeReport;

import java.util.List;

public interface FireModeService extends ReportCreatorService<FireModeReport> {

    void updateThermocoupleCount(FireModeReport report, Integer thermocoupleCount);

    void updateLinearityCoefficient(FireModeReport report, Double linearityCoefficient);

    void updateDispersionCoefficient(FireModeReport report, Double dispersionCoefficient);

    void addInterpolationPoints(FireModeReport report, List<InterpolationPoint> pointsToAdd);

    void removeInterpolationPoints(FireModeReport report, List<InterpolationPoint> pointsToRemove);

}
