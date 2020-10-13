package io.github.therealmone.fireres.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coefficient {

    private Integer fromTime;
    private Integer toTime;
    private Double value;

}
