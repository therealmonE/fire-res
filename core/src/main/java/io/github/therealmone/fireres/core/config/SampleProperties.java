package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private List<TemperaturePoint> interpolationPoints;
    private RandomPointsProperties randomPoints;
    private Integer thermocoupleCount;

}
