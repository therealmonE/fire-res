package io.github.therealmone.fireres.core.firemode.report;

import io.github.therealmone.fireres.core.common.report.Report;
import io.github.therealmone.fireres.core.firemode.model.FireModeSample;
import io.github.therealmone.fireres.core.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.core.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.firemode.model.StandardTemperature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireModeReport implements Report {

    private StandardTemperature standardTemperature;
    private MinAllowedTemperature minAllowedTemperature;
    private MaxAllowedTemperature maxAllowedTemperature;
    private FurnaceTemperature furnaceTemperature;
    private List<FireModeSample> samples;

}
