package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.config.excess.pressure.ExcessPressureProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralProperties {

    private String fileName;

    private Integer time;

    private Integer environmentTemperature;

    @Optional
    @Builder.Default
    private ExcessPressureProperties excessPressure = new ExcessPressureProperties();

    @Builder.Default
    private List<ReportType> includedReports = new ArrayList<>();

}
