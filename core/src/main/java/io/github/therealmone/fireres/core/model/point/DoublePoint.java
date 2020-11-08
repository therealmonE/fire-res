package io.github.therealmone.fireres.core.model.point;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DoublePoint extends Point<Double> {

    @Builder
    public DoublePoint(Integer time, Double value) {
        super(time, value);
    }

    @Override
    public IntegerPoint normalize(Integer shift) {
        return IntegerPoint.builder()
                .time(getTime())
                .value((int) (getValue() * Math.pow(10, shift)))
                .build();
    }

}
