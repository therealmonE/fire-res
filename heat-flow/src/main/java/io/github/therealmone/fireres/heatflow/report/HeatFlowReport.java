package io.github.therealmone.fireres.heatflow.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowBound;
import io.github.therealmone.fireres.heatflow.model.HeatFlowMeanTemperature;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSensorTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class HeatFlowReport implements Report<HeatFlowProperties> {

    private final UUID id;
    private final Sample sample;

    private HeatFlowBound bound;
    private HeatFlowMeanTemperature meanTemperature;
    private List<HeatFlowSensorTemperature> sensorTemperatures;

    @Override
    public HeatFlowProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow();
    }
}