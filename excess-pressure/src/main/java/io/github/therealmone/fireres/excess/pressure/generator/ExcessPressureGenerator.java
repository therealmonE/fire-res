package io.github.therealmone.fireres.excess.pressure.generator;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.SamplePressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
public class ExcessPressureGenerator implements PointSequenceGenerator<SamplePressure> {

    private final Integer time;

    private final MinAllowedPressure minAllowedPressure;
    private final MaxAllowedPressure maxAllowedPressure;

    @Override
    public SamplePressure generate() {
        val points = IntStream.range(0, time)
                .mapToObj(i -> {
                    val min = minAllowedPressure.getPoint(i).getValue();
                    val max = maxAllowedPressure.getPoint(i).getValue();

                    return new DoublePoint(i, generateValueInInterval(min, max));
                })
                .collect(Collectors.toList());

        return new SamplePressure(points);
    }
}
