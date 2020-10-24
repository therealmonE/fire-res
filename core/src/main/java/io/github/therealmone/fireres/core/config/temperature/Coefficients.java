package io.github.therealmone.fireres.core.config.temperature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coefficients {

     private List<Coefficient> coefficients;

     public Coefficient getCoefficient(Integer time) {
         return coefficients.stream()
                 .filter(c -> c.getFrom() <= time && c.getTo() >= time)
                 .findFirst()
                 .orElseThrow();
     }

}
