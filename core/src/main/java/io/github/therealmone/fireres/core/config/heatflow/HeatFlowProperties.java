package io.github.therealmone.fireres.core.config.heatflow;

import io.github.therealmone.fireres.core.config.Interpolated;
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
public class HeatFlowProperties extends Interpolated {

    private Integer sensorCount;
    private Integer bound;

}
