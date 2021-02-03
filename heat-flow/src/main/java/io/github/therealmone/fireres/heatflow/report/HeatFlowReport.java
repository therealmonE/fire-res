package io.github.therealmone.fireres.heatflow.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.heatflow.model.HeatFlowBound;
import io.github.therealmone.fireres.heatflow.model.HeatFlowMeanTemperature;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSensorTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class HeatFlowReport implements Report {

    private final Sample sample;
    private final UUID id = UUID.randomUUID();

    private HeatFlowBound bound;
    private HeatFlowMeanTemperature meanTemperature;
    private List<HeatFlowSensorTemperature> sensorTemperatures;

}