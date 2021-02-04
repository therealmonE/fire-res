package io.github.therealmone.fireres.core.service;

import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.model.Report;

import java.util.List;

public interface InterpolationService<R extends Report> {

    void updateLinearityCoefficient(R report, Double linearityCoefficient);

    void updateDispersionCoefficient(R report, Double dispersionCoefficient);

    void addInterpolationPoints(R report, List<InterpolationPoint> pointsToAdd);

    void removeInterpolationPoints(R report, List<InterpolationPoint> pointsToRemove);

}
