package io.github.therealmone.fireres.core.model.point;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TemperaturePoint extends Point<Integer> {

    @Builder
    public TemperaturePoint(Integer time, Integer value) {
        super(time, value);
    }

    @Override
    public IntegerPoint normalize(Integer shift) {
        return IntegerPoint.builder()
                .time(getTime())
                .value(getValue())
                .build();
    }
}
