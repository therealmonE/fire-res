package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.config.excess.pressure.ExcessPressureProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralProperties {

    private String fileName;
    private Integer time;
    private Integer environmentTemperature;
    private ExcessPressureProperties excessPressure;
    private List<ReportType> includedReports;

}
