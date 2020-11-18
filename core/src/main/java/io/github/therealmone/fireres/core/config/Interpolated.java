package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Interpolated {

    @Optional
    @Builder.Default
    private List<IntegerPoint> interpolationPoints = Collections.emptyList();

    @Optional
    @Builder.Default
    private RandomPointsProperties randomPoints = RandomPointsProperties.builder()
            .enrichWithRandomPoints(true)
            .newPointChance(0.5)
            .build();

    public void setInterpolationPoints(List<IntegerPoint> interpolationPoints) {
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
        this.interpolationPoints = interpolationPoints;
    }

}
