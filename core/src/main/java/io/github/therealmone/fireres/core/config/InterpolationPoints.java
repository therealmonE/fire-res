package io.github.therealmone.fireres.core.config;

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
