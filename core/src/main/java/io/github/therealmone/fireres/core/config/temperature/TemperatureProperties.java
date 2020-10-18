package io.github.therealmone.fireres.core.config.temperature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureProperties {

    private Integer environmentTemperature;
    private Coefficients minAllowedTempCoefficients;
    private Coefficients maxAllowedTempCoefficients;

}
