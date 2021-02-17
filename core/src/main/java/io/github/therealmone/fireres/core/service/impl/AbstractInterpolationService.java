package io.github.therealmone.fireres.core.service.impl;

import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.service.InterpolationService;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractInterpolationService<R extends Report<?>> implements InterpolationService<R> {

    private final Function<R, Interpolation> propertiesMapper;

    @Override
    public void updateLinearityCoefficient(R report, Double linearityCoefficient) {
        propertiesMapper.apply(report).setLinearityCoefficient(linearityCoefficient);

        postUpdateLinearityCoefficient(report);
    }

    @Override
    public void updateDispersionCoefficient(R report, Double dispersionCoefficient) {
        propertiesMapper.apply(report).setDispersionCoefficient(dispersionCoefficient);

        postUpdateDispersionCoefficient(report);
    }

    @Override
    public void addInterpolationPoints(R report, List<InterpolationPoint> pointsToAdd) {
        val currentPoints = propertiesMapper.apply(report).getInterpolationPoints();

        if (!pointsToAdd.isEmpty()) {
            currentPoints.addAll(pointsToAdd);
            currentPoints.sort(Comparator.comparing(InterpolationPoint::getTime));

            try {
                postUpdateInterpolationPoints(report);
            } catch (Exception e) {
                currentPoints.removeAll(pointsToAdd);
                throw e;
            }
        }
    }

    @Override
    public void removeInterpolationPoints(R report, List<InterpolationPoint> pointsToRemove) {
        val currentPoints = propertiesMapper.apply(report).getInterpolationPoints();

        if (currentPoints.removeIf(pointsToRemove::contains)) {
            postUpdateInterpolationPoints(report);
        }
    }

    protected abstract void postUpdateLinearityCoefficient(R report);

    protected abstract void postUpdateDispersionCoefficient(R report);

    protected abstract void postUpdateInterpolationPoints(R report);

}
