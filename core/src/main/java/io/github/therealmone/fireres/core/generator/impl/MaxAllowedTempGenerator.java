package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.temperature.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.smoothFunction;

@RequiredArgsConstructor
@Slf4j
public class MaxAllowedTempGenerator implements PointSequenceGenerator<MaxAllowedTemperature> {

    private final Coefficients coefficients;
    private final StandardTemperature standardTemp;

    @Override
    public MaxAllowedTemperature generate() {
        log.info("Generating maximum allowed temperature with coefficients: {}, standard temperature: {}",
                coefficients.getCoefficients(), standardTemp.getValue());

        val maxAllowedTemp = IntStream.range(0, standardTemp.getValue().size())
                .mapToObj(t -> new Point(t,
                        (int) Math.round(standardTemp.getValue().get(t).getTemperature() * coefficients.getCoefficient(t).getValue())))
                .collect(Collectors.toList());

        log.info("Generated maximum allowed temperature: {}", maxAllowedTemp);
        return MaxAllowedTemperature.builder()
                .value(maxAllowedTemp)
                .smoothedValue(smoothFunction(maxAllowedTemp))
                .build();
    }

}
