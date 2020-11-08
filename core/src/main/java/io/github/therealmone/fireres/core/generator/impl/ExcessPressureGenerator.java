package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.point.DoublePoint;
import io.github.therealmone.fireres.core.model.pressure.SamplePressure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.core.utils.MathUtils.randomDoublePoint;

@RequiredArgsConstructor
@Slf4j
public class ExcessPressureGenerator implements PointSequenceGenerator<SamplePressure> {
    private final Integer time;
    private final Double delta;

    @Override
    public SamplePressure generate() {
        val points = new ArrayList<DoublePoint>();

        for (int t = 0; t <= time; t++) {
            DoublePoint point = new DoublePoint(t, randomDoublePoint(-delta, delta, 2));
            points.add(point);
        }

        return new SamplePressure(points);
    }
}
