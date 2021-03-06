package io.github.therealmone.fireres.heatflow.config;

import io.github.therealmone.fireres.core.config.BoundsShift;
import io.github.therealmone.fireres.core.model.BoundShift;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowBoundsShift implements BoundsShift {

    @Builder.Default
    private BoundShift<HeatFlowPoint> maxAllowedFlowShift = new BoundShift<>();

}
