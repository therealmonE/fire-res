package io.github.therealmone.fireres.firemode.config;

import io.github.therealmone.fireres.core.config.BoundsShift;
import io.github.therealmone.fireres.core.model.BoundShift;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireModeBoundsShift implements BoundsShift {

    @Builder.Default
    BoundShift<IntegerPoint> maxAllowedTemperatureShift = new BoundShift<>();

    @Builder.Default
    BoundShift<IntegerPoint> minAllowedTemperatureShift = new BoundShift<>();

}
