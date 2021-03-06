package io.github.therealmone.fireres.heatflow.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.MaxAllowedFlow;
import io.github.therealmone.fireres.heatflow.model.MeanTemperature;
import io.github.therealmone.fireres.heatflow.model.SensorTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class HeatFlowReport implements Report<HeatFlowProperties> {

    private final UUID id;
    private final Sample sample;

    private MaxAllowedFlow bound;
    private MeanTemperature meanTemperature;
    private List<SensorTemperature> sensorTemperatures;

    @Override
    public HeatFlowProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow();
    }
}