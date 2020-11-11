package io.github.therealmone.fireres.core.pressure.generator;

import io.github.therealmone.fireres.core.common.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.common.model.DoublePoint;
import io.github.therealmone.fireres.core.pressure.model.SamplePressure;
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
