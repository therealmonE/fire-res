package io.github.therealmone.fireres.core.config.random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomPointsProperties {

    private Boolean enrichWithRandomPoints;
    private Double newPointChance;

}
