package io.github.therealmone.fireres.firemode.model;

import io.github.therealmone.fireres.core.model.ReportSample;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FireModeSample implements ReportSample {

    private final UUID id;

    private List<ThermocoupleTemperature> thermocoupleTemperatures;
    private ThermocoupleMeanTemperature thermocoupleMeanTemperature;

}
