package io.github.therealmone.fireres.heatflow.config;

import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HeatFlowProperties extends Interpolation implements ReportProperties {

    private Integer sensorCount;
    private Integer bound;

}
