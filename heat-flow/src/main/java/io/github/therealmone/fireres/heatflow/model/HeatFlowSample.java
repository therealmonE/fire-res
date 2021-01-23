package io.github.therealmone.fireres.heatflow.model;

import io.github.therealmone.fireres.core.model.ReportSample;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class HeatFlowSample implements ReportSample {

    private final UUID id;

    private HeatFlowBound bound;
    private HeatFlowMeanTemperature meanTemperature;
    private List<HeatFlowSensorTemperature> sensorTemperatures;

}