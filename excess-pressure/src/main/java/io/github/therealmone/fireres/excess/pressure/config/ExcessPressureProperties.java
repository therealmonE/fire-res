package io.github.therealmone.fireres.excess.pressure.config;

import io.github.therealmone.fireres.core.config.BoundsShiftModifier;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressureProperties implements ReportProperties, BoundsShiftModifier<ExcessPressureBoundsShift> {

    @Builder.Default
    private Double delta = 2d;

    @Builder.Default
    private Double basePressure = 10d;

    @Builder.Default
    private Double dispersionCoefficient = 0.9999;

    @Builder.Default
    private ExcessPressureBoundsShift boundsShift = new ExcessPressureBoundsShift();

}
