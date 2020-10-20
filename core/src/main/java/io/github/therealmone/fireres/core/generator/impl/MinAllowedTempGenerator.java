package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.temperature.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class MinAllowedTempGenerator implements PointSequenceGenerator<MinAllowedTemperature> {

    private final Coefficients coefficients;
    private final StandardTemperature standardTemp;

    @Override
    public MinAllowedTemperature generate() {
        log.info("Generating minimum allowed temperature with coefficients: {}, standard temperature: {}",
                coefficients.getCoefficients(), standardTemp.getValue());

        val minAllowedTemp = IntStream.range(0, standardTemp.getValue().size())
                .mapToObj(t -> new Point(t,
                        (int) Math.round(standardTemp.getValue().get(t).getTemperature() * coefficients.getCoefficient(t).getValue())))
                .collect(Collectors.toList());

        log.info("Generated minimum allowed temperature: {}", minAllowedTemp);
        return new MinAllowedTemperature(minAllowedTemp);
    }

}
