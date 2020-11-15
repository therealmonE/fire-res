package io.github.therealmone.fireres.core.common.config;

import io.github.therealmone.fireres.core.pressure.config.ExcessPressureProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralProperties {

    private String fileName;
    private Integer time;
    private Integer environmentTemperature;
    private ExcessPressureProperties excessPressure;

}
