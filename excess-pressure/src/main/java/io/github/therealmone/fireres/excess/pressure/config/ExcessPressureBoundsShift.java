package io.github.therealmone.fireres.excess.pressure.config;

import io.github.therealmone.fireres.core.config.BoundsShift;
import io.github.therealmone.fireres.core.model.BoundShift;
import io.github.therealmone.fireres.core.model.DoublePoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressureBoundsShift implements BoundsShift {

    @Builder.Default
    private BoundShift<DoublePoint> maxAllowedPressureShift = new BoundShift<>();

    @Builder.Default
    private BoundShift<DoublePoint> minAllowedPressureShift = new BoundShift<>();

}
