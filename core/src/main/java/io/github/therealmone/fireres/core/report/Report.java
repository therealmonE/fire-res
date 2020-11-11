package io.github.therealmone.fireres.core.report;

import io.github.therealmone.fireres.core.firemode.model.FireMode;
import io.github.therealmone.fireres.core.pressure.model.ExcessPressure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    private Integer time;
    private Integer environmentTemperature;

    private FireMode fireMode;
    private ExcessPressure excessPressure;


}
