package io.github.therealmone.fireres.core.pressure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressure {

    private MinAllowedPressure minAllowedPressure;
    private MaxAllowedPressure maxAllowedPressure;
    private List<ExcessPressureSample> samples;

}
