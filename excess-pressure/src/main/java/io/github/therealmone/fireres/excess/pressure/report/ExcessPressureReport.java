package io.github.therealmone.fireres.excess.pressure.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.Pressure;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ExcessPressureReport implements Report<ExcessPressureProperties> {

    private final Sample sample;
    private final UUID id = UUID.randomUUID();

    private Pressure pressure;
    private Double basePressure;
    private MinAllowedPressure minAllowedPressure;
    private MaxAllowedPressure maxAllowedPressure;

    @Override
    public ExcessPressureProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(ExcessPressureProperties.class)
                .orElseThrow();
    }
}
