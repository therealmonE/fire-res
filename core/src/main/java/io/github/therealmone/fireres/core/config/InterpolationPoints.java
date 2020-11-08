package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@Getter
public class InterpolationPoints {

    private final List<TemperaturePoint> points;

    public InterpolationPoints(List<TemperaturePoint> points) {
        points.sort(Comparator.comparing(TemperaturePoint::getTime));
        this.points = points;
    }

}
