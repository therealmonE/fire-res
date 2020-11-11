package io.github.therealmone.fireres.core.pressure.report;

import io.github.therealmone.fireres.core.common.report.Report;
import io.github.therealmone.fireres.core.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.core.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.core.pressure.model.MinAllowedPressure;
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
