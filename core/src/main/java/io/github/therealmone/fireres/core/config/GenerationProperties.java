package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.model.Coefficients;
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

}
