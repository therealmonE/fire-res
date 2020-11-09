package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureProperties {

    private Integer environmentTemperature;

    @Optional
    @Builder.Default
    private List<Coefficient> minAllowedTempCoefficients = List.of(
            new Coefficient(0, 10, 0.85),
            new Coefficient(11, 30, 0.9),
            new Coefficient(31, 360, 0.95)
    );

    @Optional
    @Builder.Default
    private List<Coefficient> maxAllowedTempCoefficients = List.of(
            new Coefficient(0, 10, 1.15),
            new Coefficient(11, 30, 1.1),
            new Coefficient(31, 360, 1.05)
    );

}
