package io.github.therealmone.fireres.core.config.excess.pressure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressureProperties {

    private Double delta;
    private Double basePressure;

}
