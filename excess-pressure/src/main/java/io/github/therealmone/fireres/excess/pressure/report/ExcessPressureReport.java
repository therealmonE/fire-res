package io.github.therealmone.fireres.excess.pressure.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
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

    private List<ExcessPressureSample> samples;

}
