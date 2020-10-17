package io.github.therealmone.fireres.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationProperties {

    private Integer t0;
    private Integer time;
    private Coefficients minAllowedTempCoefficients;
    private Coefficients maxAllowedTempCoefficients;
    private InterpolationPoints interpolationPoints;
    private InterpolationMethod interpolationMethod;

    private Boolean enrichWithRandomPoints;
    private Double newPointChance;

}
