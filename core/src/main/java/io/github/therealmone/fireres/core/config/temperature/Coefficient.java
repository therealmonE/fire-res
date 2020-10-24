package io.github.therealmone.fireres.core.config.temperature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coefficient {

    private Integer from;
    private Integer to;
    private Double value;

}
