package io.github.therealmone.fireres.core.config.heatflow;

import io.github.therealmone.fireres.core.config.Interpolation;
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
public class HeatFlowProperties extends Interpolation {

    private Integer sensorCount;
    private Integer bound;

}
