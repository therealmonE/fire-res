package io.github.therealmone.fireres.heatflow.config;

import io.github.therealmone.fireres.core.config.BoundsShiftModifier;
import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.FunctionFormModifier;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowProperties implements
        FunctionFormModifier<Double>,
        BoundsShiftModifier<HeatFlowBoundsShift>,
        ReportProperties {

    @Builder.Default
    private Integer sensorCount = 3;

    @Builder.Default
    private Double bound = 3.5;

    @Builder.Default
    private FunctionForm<Double> functionForm = new FunctionForm<>();

    @Builder.Default
    private HeatFlowBoundsShift boundsShift = new HeatFlowBoundsShift();

}
