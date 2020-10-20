package io.github.therealmone.fireres.core.config.sample;

import io.github.therealmone.fireres.core.config.interpolation.InterpolationProperties;
import io.github.therealmone.fireres.core.config.random.RandomPointsProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private InterpolationProperties interpolation;
    private RandomPointsProperties randomPoints;
    private Integer thermocoupleCount;

}
