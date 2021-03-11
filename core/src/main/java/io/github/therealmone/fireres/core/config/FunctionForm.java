package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionForm<N extends Number> {

    @Optional
    @Builder.Default
    private List<Point<N>> interpolationPoints = new ArrayList<>();

    @Optional
    @Builder.Default
    private Double linearityCoefficient = 1d;

    @Optional
    @Builder.Default
    private Double dispersionCoefficient = 0d;

    public void setInterpolationPoints(List<Point<N>> interpolationPoints) {
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
        this.interpolationPoints = interpolationPoints;
    }

    public Double getNonLinearityCoefficient() {
        return 1 - linearityCoefficient;
    }

}
