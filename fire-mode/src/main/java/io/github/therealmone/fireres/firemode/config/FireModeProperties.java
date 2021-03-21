package io.github.therealmone.fireres.firemode.config;

import io.github.therealmone.fireres.core.config.BoundsShiftModifier;
import io.github.therealmone.fireres.core.config.FunctionFormModifier;
import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.ReportProperties;
import io.github.therealmone.fireres.firemode.model.FireModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireModeProperties implements
        ReportProperties,
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<FireModeBoundsShift> {

    private Integer thermocoupleCount;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private FireModeBoundsShift boundsShift = new FireModeBoundsShift();

    @Builder.Default
    private FireModeType fireModeType = FireModeType.LOG;

}
