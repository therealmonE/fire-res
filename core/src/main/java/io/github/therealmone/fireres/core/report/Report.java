package io.github.therealmone.fireres.core.report;

import io.github.therealmone.fireres.core.model.firemode.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.firemode.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    private Integer time;
    private Integer environmentTemperature;
    private FurnaceTemperature furnaceTemperature;
    private MinAllowedTemperature minAllowedTemperature;
    private MaxAllowedTemperature maxAllowedTemperature;
    private StandardTemperature standardTemperature;
    private List<Sample> samples;


}
