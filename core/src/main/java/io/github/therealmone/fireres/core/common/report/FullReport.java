package io.github.therealmone.fireres.core.common.report;

import io.github.therealmone.fireres.core.firemode.report.FireModeReport;
import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.core.unheated.report.UnheatedSurfaceReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullReport implements Report {

    private Integer time;
    private Integer environmentTemperature;

    private FireModeReport fireMode;
    private ExcessPressureReport excessPressure;
    private UnheatedSurfaceReport unheatedSurface;

}
