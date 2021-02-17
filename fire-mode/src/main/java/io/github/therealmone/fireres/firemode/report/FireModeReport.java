package io.github.therealmone.fireres.firemode.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FireModeReport implements Report<FireModeProperties> {

    private final Sample sample;
    private final UUID id = UUID.randomUUID();

    private StandardTemperature standardTemperature;
    private MinAllowedTemperature minAllowedTemperature;
    private MaxAllowedTemperature maxAllowedTemperature;
    private FurnaceTemperature furnaceTemperature;
    private List<ThermocoupleTemperature> thermocoupleTemperatures;
    private ThermocoupleMeanTemperature thermocoupleMeanTemperature;

    @Override
    public FireModeProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow();
    }
}
