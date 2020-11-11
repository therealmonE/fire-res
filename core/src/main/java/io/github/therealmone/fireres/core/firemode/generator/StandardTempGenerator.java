package io.github.therealmone.fireres.core.firemode.generator;

import io.github.therealmone.fireres.core.common.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.core.common.model.IntegerPoint;
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
        val standardTemp = IntStream.range(1, time)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(345 * Math.log10(8 * t + 1))))
                .collect(Collectors.toList());

        standardTemp.add(0, new IntegerPoint(0, t0));

        return new StandardTemperature(standardTemp);
    }

}