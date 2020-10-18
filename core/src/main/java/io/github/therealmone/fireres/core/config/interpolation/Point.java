package io.github.therealmone.fireres.core.config.interpolation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Point {

    private Integer time;
    private Integer temperature;

}
