package io.github.therealmone.fireres.unheated.surface.service;

import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;

import java.util.List;

public interface UnheatedSurfaceThirdGroupService {

    void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount);

    void updateBound(UnheatedSurfaceReport report, Integer bound);

    void updateLinearityCoefficient(UnheatedSurfaceReport report, Double linearityCoefficient);

    void updateDispersionCoefficient(UnheatedSurfaceReport report, Double dispersionCoefficient);

    void addInterpolationPoints(UnheatedSurfaceReport report, List<InterpolationPoint> pointsToAdd);

    void removeInterpolationPoints(UnheatedSurfaceReport report, List<InterpolationPoint> pointsToRemove);

}
