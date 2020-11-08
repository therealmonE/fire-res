package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class StandardTempGenerator implements PointSequenceGenerator<StandardTemperature> {

    private final Integer t0;
    private final Integer time;

    @Override
    public StandardTemperature generate() {
        log.trace("Generating standard temperature with  t0: {}, time: {}", t0, time);

        val standardTemp = IntStream.range(1, time)
                .mapToObj(t -> new TemperaturePoint(t,
                        (int) Math.round(345 * Math.log10(8 * t + 1))))
                .collect(Collectors.toList());

        standardTemp.add(0, new TemperaturePoint(0, t0));

        log.trace("Generated standard temperature: {}", standardTemp);
        return new StandardTemperature(standardTemp);
    }

}
