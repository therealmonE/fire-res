package io.github.therealmone.fireres.excess.pressure.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressureReport implements Report {

    private MinAllowedPressure minAllowedPressure;
    private MaxAllowedPressure maxAllowedPressure;
    private List<ExcessPressureSample> samples;

}
