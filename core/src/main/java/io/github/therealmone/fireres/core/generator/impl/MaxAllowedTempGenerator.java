package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.firemode.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.smoothMaxFunction;

@RequiredArgsConstructor
@Slf4j
public class MaxAllowedTempGenerator implements PointSequenceGenerator<MaxAllowedTemperature> {

    private final Integer time;
    private final Coefficients coefficients;
    private final StandardTemperature standardTemp;

    @Override
    public MaxAllowedTemperature generate() {
        val maxAllowedTemp = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(standardTemp.getPoint(t).getValue() * coefficients.getCoefficient(t))))
                .collect(Collectors.toList());

        return MaxAllowedTemperature.builder()
                .value(maxAllowedTemp)
                .smoothedValue(smoothMaxFunction(maxAllowedTemp))
                .build();
    }

}
