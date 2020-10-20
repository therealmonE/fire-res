package io.github.therealmone.fireres.core.config.interpolation;

import io.github.therealmone.fireres.core.model.Point;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@Getter
public class InterpolationPoints {

    private final List<Point> points;

    public InterpolationPoints(List<Point> points) {
        points.sort(Comparator.comparing(Point::getTime));
        this.points = points;
    }

}
