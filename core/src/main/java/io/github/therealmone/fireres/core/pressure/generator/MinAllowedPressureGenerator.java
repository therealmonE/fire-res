package io.github.therealmone.fireres.core.pressure.generator;

import io.github.therealmone.fireres.core.common.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.common.model.DoublePoint;
import io.github.therealmone.fireres.core.pressure.model.MinAllowedPressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MinAllowedPressureGenerator implements PointSequenceGenerator<MinAllowedPressure> {

    private final Integer time;
    private final Double delta;

    @Override
    public MinAllowedPressure generate() {
        val minAllowedPressure = IntStream.range(0, time)
                .mapToObj(t -> new DoublePoint(t, -delta))
                .collect(Collectors.toList());

        return new MinAllowedPressure(minAllowedPressure);
    }
}
