package io.github.therealmone.fireres.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterpolationPoint {

    private Integer time;
    private Integer value;

}
