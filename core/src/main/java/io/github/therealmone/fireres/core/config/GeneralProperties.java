package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.config.pressure.ExcessPressureProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralProperties {

    private Integer time;
    private Integer environmentTemperature;
    private ExcessPressureProperties excessPressure;

}
