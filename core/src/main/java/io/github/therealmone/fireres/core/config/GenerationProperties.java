package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.config.interpolation.InterpolationProperties;
import io.github.therealmone.fireres.core.config.random.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.temperature.TemperatureProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationProperties {

    private Integer time;

    private TemperatureProperties temperature;
    private InterpolationProperties interpolation;
    private RandomPointsProperties randomPoints;

    private Integer thermocoupleCount;

}
