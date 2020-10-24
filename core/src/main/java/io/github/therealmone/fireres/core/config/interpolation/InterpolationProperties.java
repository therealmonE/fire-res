package io.github.therealmone.fireres.core.config.interpolation;

import io.github.therealmone.fireres.core.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterpolationProperties {

    private List<Point> interpolationPoints;
    private InterpolationMethod interpolationMethod;

}
