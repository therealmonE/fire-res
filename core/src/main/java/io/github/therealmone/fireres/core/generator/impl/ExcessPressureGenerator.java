package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.point.DoublePoint;
import io.github.therealmone.fireres.core.model.pressure.SamplePressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
public class ExcessPressureGenerator implements PointSequenceGenerator<SamplePressure> {
    private final Integer time;
    private final Double delta;

    @Override
    public SamplePressure generate() {
        val points = IntStream.range(0, time)
                .mapToObj(i -> new DoublePoint(i, generateValueInInterval(-delta, delta)))
                .collect(Collectors.toList());

        return new SamplePressure(points);
    }
}
