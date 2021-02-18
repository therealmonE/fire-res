package io.github.therealmone.fireres.excess.pressure.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressureProperties implements ReportProperties {

    private Double delta;
    private Double basePressure;

    @Optional
    @Builder.Default
    private Double dispersionCoefficient = 0.9999;

}
