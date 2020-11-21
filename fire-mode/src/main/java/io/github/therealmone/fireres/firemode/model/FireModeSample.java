package io.github.therealmone.fireres.firemode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireModeSample {

    private List<ThermocoupleTemperature> thermocoupleTemperatures;
    private ThermocoupleMeanTemperature thermocoupleMeanTemperature;

}
