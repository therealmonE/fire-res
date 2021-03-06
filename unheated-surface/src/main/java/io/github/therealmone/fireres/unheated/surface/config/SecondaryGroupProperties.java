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
public class SecondaryGroupProperties implements
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<SecondaryGroupBoundsShift> {

    @Builder.Default
    private Integer bound = 300;

    @Builder.Default
    private Integer thermocoupleCount = 3;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private SecondaryGroupBoundsShift boundsShift = new SecondaryGroupBoundsShift();

}
