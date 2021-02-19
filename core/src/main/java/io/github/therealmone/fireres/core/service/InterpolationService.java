package io.github.therealmone.fireres.core.service;

import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.Report;

import java.util.List;

public interface InterpolationService<R extends Report, N extends Number> {

    void updateLinearityCoefficient(R report, Double linearityCoefficient);

    void updateDispersionCoefficient(R report, Double dispersionCoefficient);

    void addInterpolationPoints(R report, List<Point<N>> pointsToAdd);

    void removeInterpolationPoints(R report, List<Point<N>> pointsToRemove);

}
