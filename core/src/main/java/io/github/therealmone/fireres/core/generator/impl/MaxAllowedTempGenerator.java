package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.StandardTemperature;
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
        log.trace("Generating maximum allowed temperature with coefficients: {}, standard temperature: {}",
                coefficients.getCoefficients(), standardTemp.getValue());

        val maxAllowedTemp = IntStream.range(0, time)
                .mapToObj(t -> new Point(t,
                        (int) Math.round(standardTemp.getTemperature(t) * coefficients.getCoefficient(t))))
                .collect(Collectors.toList());

        log.trace("Generated maximum allowed temperature: {}", maxAllowedTemp);
        return MaxAllowedTemperature.builder()
                .value(maxAllowedTemp)
                .smoothedValue(smoothMaxFunction(maxAllowedTemp))
                .build();
    }

}
