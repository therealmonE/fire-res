package io.github.therealmone.fireres.heatflow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowSample {

    private HeatFlowBound bound;
    private HeatFlowMeanTemperature meanTemperature;
    private List<HeatFlowSensorTemperature> sensorTemperatures;

}