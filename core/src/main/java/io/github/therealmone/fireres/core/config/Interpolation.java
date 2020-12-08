package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
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
public class Interpolation {

    @Optional
    @Builder.Default
    private List<InterpolationPoint> interpolationPoints = Collections.emptyList();

    @Optional
    @Builder.Default
    private Double linearityCoefficient = 0.5;

    @Optional
    @Builder.Default
    private Double dispersionCoefficient = 1d;

    public void setInterpolationPoints(List<InterpolationPoint> interpolationPoints) {
        interpolationPoints.sort(Comparator.comparing(InterpolationPoint::getTime));
        this.interpolationPoints = interpolationPoints;
    }

    public Double getNonLinearityCoefficient() {
        return 1 - linearityCoefficient;
    }

}
