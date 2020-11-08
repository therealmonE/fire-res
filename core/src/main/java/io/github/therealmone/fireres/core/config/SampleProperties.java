package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import io.github.therealmone.fireres.core.model.point.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private List<IntegerPoint> interpolationPoints;
    private RandomPointsProperties randomPoints;
    private Integer thermocoupleCount;

    public void setInterpolationPoints(List<IntegerPoint> interpolationPoints) {
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
        this.interpolationPoints = interpolationPoints;
    }
}
