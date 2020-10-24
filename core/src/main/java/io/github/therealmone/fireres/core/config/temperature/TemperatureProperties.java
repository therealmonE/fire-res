package io.github.therealmone.fireres.core.config.temperature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureProperties {

    private Integer environmentTemperature;
    private List<Coefficient> minAllowedTempCoefficients;
    private List<Coefficient> maxAllowedTempCoefficients;

}
