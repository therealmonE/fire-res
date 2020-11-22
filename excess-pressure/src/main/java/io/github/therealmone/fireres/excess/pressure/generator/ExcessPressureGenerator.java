package io.github.therealmone.fireres.excess.pressure.generator;

import io.github.therealmone.fireres.core.generator.Noise;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.SamplePressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ExcessPressureGenerator implements PointSequenceGenerator<SamplePressure> {

    private final Integer time;

    private final MinAllowedPressure minAllowedPressure;
    private final MaxAllowedPressure maxAllowedPressure;

    private final Double dispersion;

    @Override
    public SamplePressure generate() {
        val pressure = IntStream.range(0, time)
                .mapToObj(t -> {
                    val min = minAllowedPressure.getPoint(t).getValue();
                    val max = maxAllowedPressure.getPoint(t).getValue();

                    return new DoublePoint(t, Noise.noise(min, max, t, dispersion));
                })
                .collect(Collectors.toList());

        return new SamplePressure(pressure);
    }
}
