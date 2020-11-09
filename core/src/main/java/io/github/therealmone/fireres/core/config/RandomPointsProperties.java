package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomPointsProperties {

    @Optional
    @Builder.Default
    private Boolean enrichWithRandomPoints = true;

    @Optional
    @Builder.Default
    private Double newPointChance = 0.5;

}
