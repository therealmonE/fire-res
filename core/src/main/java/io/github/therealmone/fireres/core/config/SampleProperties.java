package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import io.github.therealmone.fireres.core.model.point.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    @Optional
    @Builder.Default
    private List<IntegerPoint> interpolationPoints = Collections.emptyList();

    @Optional
    @Builder.Default
    private RandomPointsProperties randomPoints = RandomPointsProperties.builder()
            .enrichWithRandomPoints(true)
            .newPointChance(0.5)
            .build();

    private Integer thermocoupleCount;

    public void setInterpolationPoints(List<IntegerPoint> interpolationPoints) {
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
        this.interpolationPoints = interpolationPoints;
    }
}
