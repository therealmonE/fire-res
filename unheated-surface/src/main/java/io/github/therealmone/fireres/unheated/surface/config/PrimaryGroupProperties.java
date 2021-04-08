package io.github.therealmone.fireres.unheated.surface.config;

import io.github.therealmone.fireres.core.config.BoundsShiftModifier;
import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.FunctionFormModifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PrimaryGroupProperties implements
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<PrimaryGroupBoundsShift> {

    @Builder.Default
    private Integer thermocoupleCount = 3;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private PrimaryGroupBoundsShift boundsShift = new PrimaryGroupBoundsShift();

}
